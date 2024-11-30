package com.test.bidon.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.test.bidon.domain.Message;
import com.test.bidon.dto.LiveBidRoomUserDTO;
import com.test.bidon.dto.UserInfoDTO;
import com.test.bidon.entity.LiveAuctionPartSummary;
import com.test.bidon.repository.LiveAuctionPartRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.bidon.domain.LiveBidRoom;
import com.test.bidon.repository.LiveBidRoomRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static com.test.bidon.util.BidRoomUtil.*;

@RequiredArgsConstructor
@Slf4j
@Service
public class LiveBidService {
    private final LiveBidRoomRepository liveBidRoomRepository;
    private final LiveAuctionPartRepository liveAuctionPartRepository;

    private static final ObjectMapper objectMapper = new ObjectMapper();


    public List<LiveBidRoom> findAll() {
        return liveBidRoomRepository.findAll();
    }

    public LiveBidRoom findRoomById(Long roomId) {
        return liveBidRoomRepository.findById(roomId);
    }

    public LiveBidRoom createRoom(Long roomId) {
        LiveBidRoom chatRoom = LiveBidRoom.of(roomId);
        liveBidRoomRepository.save(roomId, chatRoom);
        return chatRoom;
    }

    public void handleAction(
            Long roomId,
            WebSocketSession session,
            Message inMessage
    ) throws JsonProcessingException {
        LiveBidRoom room = findRoomById(roomId);

        if (room == null) {
            room = createRoom(roomId);
        }

        if (isEnterRoom(inMessage)) {
            UserInfoDTO newUser = convert(inMessage.getPayload(), UserInfoDTO.class);

            LiveAuctionPartSummary liveAuctionPart = createOrGetLiveAuctionPart(newUser.getId(), Long.valueOf(roomId));

            LiveBidRoomUserDTO newBidRoomUser = LiveBidRoomUserDTO.builder()
                    .partId(liveAuctionPart.getId())
                    .userId(liveAuctionPart.getUserInfoId())
                    .email(newUser.getEmail())
                    .name(newUser.getName())
                    .profile(newUser.getProfile())
                    .national(newUser.getNational())
                    .tel(newUser.getTel())
                    .build();

            room.join(session, newBidRoomUser);

            LocalDateTime now = LocalDateTime.now();

            Message outPartsMessage = Message.builder()
                    .roomId(roomId)
                    .type("PARTS")
                    .payload(room.getRoomUsers())
                    .createTime(now)
                    .build();

            Message outEnterMessage = Message.builder()
                    .roomId(roomId)
                    .type("ENTER")
                    .payload(newBidRoomUser)
                    .createTime(now)
                    .build();

            room.sendMessageAll(toTextMessage(outPartsMessage));
            room.sendMessageExclude(toTextMessage(outEnterMessage), session);
        }

    }

    // TODO: Part에 종료 시간 추가. 나가면 종료 컬럼 삽입. 다시 못들어오게 하기.
    public LiveAuctionPartSummary createOrGetLiveAuctionPart(Long userInfoId, Long liveAuctionItemId) {

        Optional<LiveAuctionPartSummary> existingPart = liveAuctionPartRepository.findByUserInfoIdAndLiveAuctionItemId(userInfoId, liveAuctionItemId);

        if (existingPart.isPresent()) {
            return existingPart.get();
        } else {
            LiveAuctionPartSummary liveAuctionPart = new LiveAuctionPartSummary();

            liveAuctionPart.updateUserInfoId(userInfoId);
            liveAuctionPart.updateLiveAuctionItemId(liveAuctionItemId);
            liveAuctionPart.updateCreateTime(LocalDateTime.now());

            return liveAuctionPartRepository.save(liveAuctionPart);
        }
    }

    private boolean isEnterRoom(Message message) {
        return message.getType().equals("ENTER");
    }

}