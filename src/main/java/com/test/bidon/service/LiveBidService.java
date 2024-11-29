package com.test.bidon.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.bidon.domain.LiveBidRoom;
import com.test.bidon.domain.Message;
import com.test.bidon.repository.LiveBidRoomRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class LiveBidService {
    private final LiveBidRoomRepository liveBidRoomRepository;
    private static final ObjectMapper objectMapper = new ObjectMapper();


    public List<LiveBidRoom> findAll() {
        return liveBidRoomRepository.findAll();
    }

    public LiveBidRoom findRoomById(String roomId) {
        return liveBidRoomRepository.findById(roomId);
    }

    public LiveBidRoom createRoom(String roomId) {
        LiveBidRoom chatRoom = LiveBidRoom.of(roomId);
        liveBidRoomRepository.save(roomId, chatRoom);
        return chatRoom;
    }

    public void handleAction(
            String roomId,
            WebSocketSession session,
            Message message
    ) throws JsonProcessingException {
        LiveBidRoom room = findRoomById(roomId);

        System.out.println("room = " + room);

        if (room == null) {
            room = createRoom(roomId);
            System.out.println("created room = " + room);
        }

        if (isEnterRoom(message)) {
            room.join(session);
            message.setContent(message.getUserId() + "님 환영합니다.");
        }

        TextMessage textMessage = new TextMessage(objectMapper.writeValueAsString(message));
        room.sendMessage(textMessage, session);
    }

    private boolean isEnterRoom(Message chatMessage) {
        return chatMessage.getType().equals("IN");
    }

}