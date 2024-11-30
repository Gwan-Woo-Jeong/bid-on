package com.test.bidon.domain;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import com.test.bidon.dto.LiveBidRoomUserDTO;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LiveBidRoom {
    private final Long roomId;
    private final Set<WebSocketSession> sessions = new HashSet<>();
    private final Set<LiveBidRoomUserDTO> roomUsers = new HashSet<>();

    @Builder
    public LiveBidRoom(Long roomId) {
        this.roomId = roomId;
    }

    public void sendMessageExclude(TextMessage message, WebSocketSession excludeSession) {
        this.getSessions()
                .parallelStream()
                .filter(WebSocketSession::isOpen)
                .filter(session -> !session.equals(excludeSession))
                .forEach(session -> sendMessageToSession(session, message));
    }

    public void sendMessageAll(TextMessage message) {
        this.getSessions()
                .parallelStream()
                .filter(WebSocketSession::isOpen)
                .forEach(session -> sendMessageToSession(session, message));
    }

    public void sendMessageToSession(WebSocketSession session, TextMessage message) {
        try {
            session.sendMessage(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void join(WebSocketSession session, LiveBidRoomUserDTO roomUser) {
        sessions.add(session);
        roomUsers.add(roomUser);
    }

    public static LiveBidRoom of(Long roomId) {
        return LiveBidRoom.builder()
                .roomId(roomId)
                .build();
    }
}
