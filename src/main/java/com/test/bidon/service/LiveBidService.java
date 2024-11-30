package com.test.bidon.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.bidon.domain.LiveBidRoom;
import com.test.bidon.domain.Message;
import com.test.bidon.dto.LiveAuctionItemListDTO;
import com.test.bidon.repository.LiveAuctionItemRepository;
import com.test.bidon.repository.LiveBidRoomRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class LiveBidService {
    private final LiveBidRoomRepository liveBidRoomRepository;
    private static final ObjectMapper objectMapper = new ObjectMapper();
    

    private final LiveAuctionItemRepository liveAuctionItemRepository;


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

        System.out.println("inMessage = " + inMessage);

        LiveBidRoom room = findRoomById(roomId);

        if (room == null) {
            room = createRoom(roomId);
        }

        if (isEnter(inMessage)) {
            UserInfoDTO newUser = convert(inMessage.getPayload(), UserInfoDTO.class);

            LiveAuctionPartSummary liveAuctionPart = createPart(newUser.getId(), roomId);

            LiveBidRoomUserDTO newBidRoomUser = LiveBidRoomUserDTO.builder()
                    .partId(liveAuctionPart.getId())
                    .userId(liveAuctionPart.getUserInfoId())
                    .email(newUser.getEmail())
                    .name(newUser.getName())
                    .profile(newUser.getProfile())
                    .national(newUser.getNational())
                    .tel(newUser.getTel())
                    .build();

            room.enter(session, newBidRoomUser);

            sendPartsMessage(roomId, room);

            Message outEnterMessage = Message.builder()
                    .roomId(roomId)
                    .type("ENTER")
                    .payload(newBidRoomUser)
                    .createTime(inMessage.getCreateTime())
                    .build();

            room.sendMessageExclude(toTextMessage(outEnterMessage), session);

        } else if (isLeave(inMessage)) {

            Long senderId = inMessage.getSenderId();
            LiveBidRoomUserDTO foundUser = room.findRoomUser(senderId);

            if (foundUser == null) {
                return;
            }

            updatePartEndTime(senderId, roomId);
            room.leave(session, senderId);

            Message outLeaveMessage = Message.builder()
                    .roomId(roomId)
                    .type("LEAVE")
                    .payload(foundUser)
                    .createTime(inMessage.getCreateTime())
                    .build();

            sendPartsMessage(roomId, room);
            room.sendMessageAll(toTextMessage(outLeaveMessage));
        }
    }

    private static void sendPartsMessage(Long roomId, LiveBidRoom room) {

        Message outPartsMessage = Message.builder()
                .roomId(roomId)
                .type("PARTS")
                .payload(room.getRoomUsers())
                .createTime(LocalDateTime.now())
                .build();

        room.sendMessageAll(toTextMessage(outPartsMessage));
    }

    public LiveAuctionPartSummary createPart(Long userInfoId, Long liveAuctionItemId) {
        LiveAuctionPartSummary liveAuctionPart = new LiveAuctionPartSummary();

        liveAuctionPart.updateUserInfoId(userInfoId);
        liveAuctionPart.updateLiveAuctionItemId(liveAuctionItemId);
        liveAuctionPart.updateCreateTime(LocalDateTime.now());

        return liveAuctionPartRepository.save(liveAuctionPart);
    }

    public void updatePartEndTime(Long userInfoId, Long liveAuctionItemId) {
        Optional<LiveAuctionPartSummary> liveAuctionPart = liveAuctionPartRepository.findFirstByUserInfoIdAndLiveAuctionItemIdOrderByCreateTimeDesc(userInfoId, liveAuctionItemId);

        if (liveAuctionPart.isEmpty()) {
            return;
        }

        LiveAuctionPartSummary liveAuctionPartSummary = liveAuctionPart.get();
        liveAuctionPartSummary.updateEndTime(LocalDateTime.now());
        liveAuctionPartRepository.save(liveAuctionPartSummary);
    }

    private boolean isEnter(Message message) {
        return message.getType().equals("ENTER");
    }

    private boolean isLeave(Message message) {
        return message.getType().equals("LEAVE");
    }
    
    
  
    
    
    
    
    

}