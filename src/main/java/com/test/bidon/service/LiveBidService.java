package com.test.bidon.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.test.bidon.domain.LiveBidInfo;
import com.test.bidon.domain.Message;
import com.test.bidon.domain.LiveBidRoomUser;
import com.test.bidon.dto.UserInfoDTO;
import com.test.bidon.entity.LiveAuctionPartSummary;
import com.test.bidon.repository.LiveAuctionPartRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.core.JsonProcessingException;
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

    public List<LiveBidRoom> findAll() {
        return liveBidRoomRepository.findAll();
    }

    public LiveBidRoom findRoomById(Long roomId) {
        return liveBidRoomRepository.findById(roomId);
    }

    public LiveBidRoom createRoom(Long roomId) {
        LiveBidRoom liveBidRoom = LiveBidRoom.of(roomId);
        liveBidRoomRepository.save(roomId, liveBidRoom);
        return liveBidRoom;
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

        LiveBidRoomUser highestBidder = room.getHighestBidder();

        if (isEnter(inMessage)) {
            UserInfoDTO newUser = convert(inMessage.getPayload(), UserInfoDTO.class);

            LiveAuctionPartSummary liveAuctionPart = createPart(newUser.getId(), roomId);
            Long userInfoId = liveAuctionPart.getUserInfoId();

            LiveBidRoomUser newBidRoomUser = LiveBidRoomUser.builder()
                    .partId(liveAuctionPart.getId())
                    .userId(userInfoId)
                    .email(newUser.getEmail())
                    .name(newUser.getName())
                    .profile(newUser.getProfile())
                    .national(newUser.getNational())
                    .isHighestBidder(highestBidder != null && highestBidder.getUserId().equals(userInfoId))
                    .tel(newUser.getTel())
                    .build();

            room.enter(session, newBidRoomUser);

            Message outEnterMessage = Message.builder()
                    .roomId(roomId)
                    .type("ENTER")
                    .payload(newBidRoomUser)
                    .createTime(inMessage.getCreateTime())
                    .build();

            LocalDateTime now = LocalDateTime.now();

            Message alertMessage = Message.builder()
                    .roomId(roomId)
                    .type("ALERT")
                    .text(newBidRoomUser.getName() + "님이 입장하셨습니다.")
                    .createTime(now)
                    .build();

            LiveBidInfo liveBidInfo = LiveBidInfo.builder()
                    .highestBidder(highestBidder)
                    .highestBidPrice(room.getHighestBidPrice())
                    .build();

            Message outBidMessage = Message.builder()
                    .roomId(roomId)
                    .type("BID-START")
                    .payload(liveBidInfo)
                    .createTime(now)
                    .build();

            sendPartsMessage(roomId, room);
            room.sendMessageAll(toTextMessage(alertMessage));
            room.sendMessageExclude(toTextMessage(outEnterMessage), session);
            room.sendMessageToSession(session, toTextMessage(outBidMessage));

        } else if (isLeave(inMessage)) {

            Long senderId = inMessage.getSenderId();
            LiveBidRoomUser foundUser = room.findRoomUser(senderId);

            if (foundUser == null) {
                return;
            }

            updatePartEndTime(senderId, roomId);
            room.leave(session, senderId);

            Message alertMessage = Message.builder()
                    .roomId(roomId)
                    .type("ALERT")
                    .text(foundUser.getName() + "님이 나가셨습니다.")
                    .createTime(LocalDateTime.now())
                    .build();

            sendPartsMessage(roomId, room);
            room.sendMessageAll(toTextMessage(alertMessage));

        } else if (isTalk(inMessage)) {

            Long senderId = inMessage.getSenderId();
            LiveBidRoomUser foundUser = room.findRoomUser(senderId);

            Message outTalkMessage = Message.builder()
                    .roomId(roomId)
                    .type("TALK")
                    .text(inMessage.getText())
                    .payload(foundUser)
                    .createTime(inMessage.getCreateTime())
                    .build();

            room.sendMessageExclude(toTextMessage(outTalkMessage), session);
        } else if (isBid(inMessage)) {
            Integer bidPrice = inMessage.getBidPrice();
            Integer highestBidPrice = room.getHighestBidPrice();

            Message.MessageBuilder outBidMessageBuilder = Message.builder()
                    .roomId(roomId)
                    .createTime(LocalDateTime.now());

            if (bidPrice <= highestBidPrice) {
                //TODO: builder 로직 중복 제거
                LiveBidInfo liveBidInfo = LiveBidInfo.builder()
                        .highestBidder(highestBidder)
                        .highestBidPrice(room.getHighestBidPrice())
                        .build();

                outBidMessageBuilder = outBidMessageBuilder
                        .type("BID-FAIL")
                        .text("입찰가는 최고 입찰가보다 높아야합니다.")
                        .payload(liveBidInfo);

                room.sendMessageToSession(session, toTextMessage(outBidMessageBuilder.build()));
            } else {

                LiveBidRoomUser foundUser = room.updateHighestBidder(inMessage.getSenderId());

                if (foundUser == null) {
                    return;
                }

                room.setHighestBidder(foundUser);
                room.setHighestBidPrice(bidPrice);

                LiveBidInfo liveBidInfo = LiveBidInfo.builder()
                        .highestBidder(foundUser)
                        .highestBidPrice(bidPrice)
                        .build();

                outBidMessageBuilder = outBidMessageBuilder
                        .roomId(roomId)
                        .type("BID-OK")
                        .payload(liveBidInfo);

                sendPartsMessage(roomId, room);
                room.sendMessageAll(toTextMessage(outBidMessageBuilder.build()));
            }
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

    private boolean isTalk(Message message) {
        return message.getType().equals("TALK");
    }

    private boolean isBid(Message message) {
        return message.getType().equals("BID");
    }
}