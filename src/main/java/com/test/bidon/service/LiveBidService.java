package com.test.bidon.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

import com.test.bidon.domain.LiveBidInfo;
import com.test.bidon.domain.Message;
import com.test.bidon.domain.LiveBidRoomUser;
import com.test.bidon.dto.LiveBidCostDTO;
import com.test.bidon.dto.UserInfoDTO;
import com.test.bidon.entity.*;
import com.test.bidon.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.test.bidon.domain.LiveBidRoom;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static com.test.bidon.util.BidRoomUtil.*;

@RequiredArgsConstructor
@Slf4j
@Service
public class LiveBidService {
    private final LiveBidRoomRepository liveBidRoomRepository;
    private final LiveAuctionPartRepository liveAuctionPartRepository;
    private final LiveBidCostRepository liveBidCostRepository;
    private final LiveAuctionItemRepository liveAuctionItemRepository;
    private final UserRepository userRepository;
    private final WinningBidRepository winningBidRepository;

    public static final String WELCOME_MESSAGE = """
            실시간 경매에 오신 것을 환영합니다!
            입찰 버튼을 누르시면 본격적인 경매가 시작됩니다.
            경매 진행 방식은 아주 간단합니다.
            마지막 최고 금액을 입찰하신 분이 경매품을 획득할 수 있습니다.
            그럼 준비가 되셨으면 입찰 버튼을 눌러 경매를 시작해주세요!""";

    public static final String AUCTION_RUNNING_MESSAGE = """
            경매가 진행중입니다.
            입찰 가능 시간이 종료되기 전에 입찰하여 경매품을 획득해보세요!
            """;

    private static final String[] EXCLAMATIONS = {
            "대단합니다!",
            "최고입니다!",
            "와우! 와우!",
            "엄청나요!",
            "대박입니다!",
            "놀라워요!",
            "경이롭습니다!",
            "예술입니다!",
            "정말 짱입니다!",
            "환상적입니다!"
    };

    private static final String[] QUESTION_MESSAGES = {
            "원보다 더 높은 입찰이 있을까요?",
            "원보다 누가 더 높게 입찰할 수 있을까요?",
            "원보다 더 높은 금액을 제시하는 분이 있을까요?",
            "원입니다.\n이제 누가 더 높은 금액을 제시할까요?",
            "원입니다.\n이제 누가 더 높은 금액으로 도전할까요?",
            "원입니다.\n이제 누가 더 높은 금액을 제안할까요?",
            "원입니다.\n이제 누가 더 높은 금액으로 이길까요?"
    };

    private static final String EOL = "\n";

    private static String getRandomExclamation() {
        int i = (int) (Math.random() * EXCLAMATIONS.length);
        return EXCLAMATIONS[i];
    }

    private static String getRandomQuestion() {
        int i = (int) (Math.random() * QUESTION_MESSAGES.length);
        return QUESTION_MESSAGES[i];
    }

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

    @Transactional
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
        LocalDateTime createTime = inMessage.getCreateTime();

        if (isEnter(inMessage)) {
            UserInfoDTO newUser = convert(inMessage.getPayload(), UserInfoDTO.class);

            LiveAuctionPart liveAuctionPart = getLiveAuctionPart(roomId, newUser);

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
                    .createTime(createTime)
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

            Message introMessage = Message.builder()
                    .roomId(roomId)
                    .type("ALERT")
                    .text(getIntro(room))
                    .createTime(now)
                    .build();

            room.sendMessageToSession(session, toTextMessage(introMessage));
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
                    .createTime(createTime)
                    .build();

            room.sendMessageExclude(toTextMessage(outTalkMessage), session);
        } else if (isBid(inMessage)) {
            Integer bidPrice = inMessage.getBidPrice();
            Integer highestBidPrice = room.getHighestBidPrice();

            Message.MessageBuilder outBidMessageBuilder = Message.builder()
                    .roomId(roomId)
                    .createTime(LocalDateTime.now());

            if (room.getIsTimerRunning() && room.getRemainingSeconds() <= 0) {
                LiveBidInfo liveBidInfo = LiveBidInfo.builder()
                        .highestBidder(highestBidder)
                        .highestBidPrice(room.getHighestBidPrice())
                        .build();

                outBidMessageBuilder = outBidMessageBuilder
                        .type("BID-FAIL")
                        .text("경매가 종료되었습니다.")
                        .payload(liveBidInfo);

                room.sendMessageToSession(session, toTextMessage(outBidMessageBuilder.build()));
                return;
            }

            if (bidPrice <= highestBidPrice) {
                // TODO: builder 로직 중복 제거
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

                LiveBidCostDTO highestBidCost = saveBidCost(roomId, foundUser, bidPrice, createTime).toDTO();

                room.setHighestBidder(foundUser);
                room.setHighestBidPrice(bidPrice);
                room.setHighestBidCost(highestBidCost);

                LiveBidInfo liveBidInfo = LiveBidInfo.builder()
                        .highestBidder(foundUser)
                        .highestBidPrice(bidPrice)
                        .build();

                Message outBidTalkMessage = Message.builder()
                        .roomId(roomId)
                        .type("BID-TALK")
                        .text(bidPrice + "원 입찰합니다.")
                        .payload(foundUser)
                        .createTime(createTime)
                        .build();

                outBidMessageBuilder = outBidMessageBuilder
                        .roomId(roomId)
                        .type("BID-OK")
                        .payload(liveBidInfo);

                room.sendMessageAll(toTextMessage(outBidTalkMessage));
                sendPartsMessage(roomId, room);
                room.sendMessageAll(toTextMessage(outBidMessageBuilder.build()));

                TimerTask task = getTimerTask(roomId, room);

                if (room.getIsTimerRunning()) {
                    room.resetTimer(task);

                    Message outAlertMessage = Message.builder()
                            .roomId(roomId)
                            .type("ALERT")
                            .text(getRandomExclamation() + EOL + bidPrice + getRandomQuestion())
                            .createTime(LocalDateTime.now())
                            .build();

                    room.sendMessageAll(toTextMessage(outAlertMessage));
                } else {
                    room.setIsTimerRunning(true);
                    room.startTimer(task);

                    Message outAlertMessage = Message.builder()
                            .roomId(roomId)
                            .type("ALERT")
                            .text("경매 시작합니다!" + EOL + foundUser.getName() + "님이 입찰하셨습니다.")
                            .createTime(LocalDateTime.now())
                            .build();

                    room.sendMessageAll(toTextMessage(outAlertMessage));
                }

            }
        }
    }

    private LiveBidCost saveBidCost(Long roomId, LiveBidRoomUser foundUser, Integer bidPrice, LocalDateTime createTime) {
        LiveAuctionItem liveAuctionItem = liveAuctionItemRepository.getReferenceById(roomId);
        LiveAuctionPart liveAuctionPart = liveAuctionPartRepository.getReferenceById(foundUser.getPartId());

        LiveBidCost liveBidCost = LiveBidCost.builder()
                .liveAuctionPartId(liveAuctionPart.getId())
                .liveAuctionItemId(liveAuctionItem.getId())
                .bidPrice(bidPrice)
                .liveAuctionPart(liveAuctionPart)
                .liveAuctionItem(liveAuctionItem)
                .bidTime(createTime)
                .build();

        return liveBidCostRepository.save(liveBidCost);
    }

    private static String getIntro(LiveBidRoom room) {
        if (!room.getIsTimerRunning()) {
            return WELCOME_MESSAGE;
        } else {
            return AUCTION_RUNNING_MESSAGE;
        }
    }

    private TimerTask getTimerTask(Long roomId, LiveBidRoom room) {
        return new TimerTask() {
            @Override
            public void run() {
                int remainingSeconds = room.getRemainingSeconds();

                Message outTimerMessage = Message.builder()
                        .roomId(roomId)
                        .type("TIMER")
                        .remainingSeconds(remainingSeconds)
                        .createTime(LocalDateTime.now())
                        .build();

                if (remainingSeconds > 0) {
                    System.out.println("남은 시간: " + remainingSeconds + "초");

                    if (remainingSeconds == 30) {
                        Message outAlertMessage = Message.builder()
                                .roomId(roomId)
                                .type("ALERT")
                                .text("입찰 가능 시간이 30초밖에 남지 않았습니다!\n서둘러 입찰해 주시기 바랍니다.")
                                .createTime(LocalDateTime.now())
                                .build();

                        room.sendMessageAll(toTextMessage(outAlertMessage));
                    } else if (remainingSeconds == 10) {
                        Message outAlertMessage = Message.builder()
                                .roomId(roomId)
                                .type("ALERT")
                                .text("입찰 가능 시간이 단 10초밖에 남지 않았습니다!\n 서둘러 입찰해 주시기 바랍니다.\n마지막 기회를 놓치지 마세요!")
                                .createTime(LocalDateTime.now())
                                .build();

                        room.sendMessageAll(toTextMessage(outAlertMessage));
                    }
                } else {
                    System.out.println("타이머가 끝났습니다.");

                    Timer timer = room.getTimer();
                    if (timer != null) {
                        timer.cancel();
                        timer.purge();
                    }

                    LiveBidRoomUser highestBidder = room.getHighestBidder();

                    Message outAlertMessage = Message.builder()
                            .roomId(roomId)
                            .type("ALERT")
                            .text("땅땅땅! 경매가 종료되었습니다.\n" + highestBidder.getName() + "님이 최종 입찰가\n" + room.getHighestBidPrice() + "원으로 낙찰되셨습니다.\n진심으로 축하드립니다!!")
                            .createTime(LocalDateTime.now())
                            .build();

                    room.sendMessageAll(toTextMessage(outAlertMessage));

                    sendBidEndMessage(highestBidder, roomId, room);
                    saveWinningBid(room);
                    updateItemEndTime(roomId);
                }

                room.sendMessageAll(toTextMessage(outTimerMessage));
                room.setRemainingSeconds(remainingSeconds - 1);
            }
        };
    }

    private static void sendBidEndMessage(LiveBidRoomUser highestBidder, Long roomId, LiveBidRoom room) {
        Message outBidEndMessage = Message.builder()
                .roomId(roomId)
                .type("BID-END")
                .payload(highestBidder)
                .createTime(LocalDateTime.now())
                .build();

        room.sendMessageAll(toTextMessage(outBidEndMessage));
    }

    private void updateItemEndTime(Long roomId) {
        Optional<LiveAuctionItem> liveAuctionItem = liveAuctionItemRepository.findById(roomId);

        if (liveAuctionItem.isEmpty()) {
            return;
        }

        LiveAuctionItem item = liveAuctionItem.get();
        item.updateEndTime(LocalDateTime.now());
        liveAuctionItemRepository.save(item);
    }

    private void saveWinningBid(LiveBidRoom room) {
        LiveBidCostDTO highestBidCost = room.getHighestBidCost();

        WinningBid winningBid = WinningBid.builder()
                .userInfoId(highestBidCost.getLiveAuctionPart().getUserInfoId())
                .liveBidId(highestBidCost.getId())
                .build();

        winningBidRepository.save(winningBid);
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

    private LiveAuctionPart getLiveAuctionPart(Long roomId, UserInfoDTO newUser) {
        UserEntity user = userRepository.getReferenceById(newUser.getId());
        LiveAuctionItem item = liveAuctionItemRepository.getReferenceById(roomId);

        LiveAuctionPart liveAuctionPartEntity = LiveAuctionPart.builder()
                .userInfoId(user.getId())
                .liveAuctionItemId(item.getId())
                .liveAuctionItem(item)
                .userInfo(user)
                .createTime(LocalDateTime.now())
                .build();

        return liveAuctionPartRepository.save(liveAuctionPartEntity);
    }

    public void updatePartEndTime(Long userInfoId, Long liveAuctionItemId) {
        Optional<LiveAuctionPart> liveAuctionPart = liveAuctionPartRepository.findFirstByUserInfoIdAndLiveAuctionItemIdOrderByCreateTimeDesc(userInfoId, liveAuctionItemId);

        if (liveAuctionPart.isEmpty()) {
            return;
        }

        LiveAuctionPart auctionPart = liveAuctionPart.get();
        auctionPart.updateEndTime(LocalDateTime.now());
        liveAuctionPartRepository.save(auctionPart);
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