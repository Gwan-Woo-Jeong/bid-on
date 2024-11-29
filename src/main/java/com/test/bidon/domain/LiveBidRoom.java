package com.test.bidon.domain;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LiveBidRoom {
    private final String roomId;
    private final Set<WebSocketSession> sessions = new HashSet<>();

    @Builder
    public LiveBidRoom(String roomId) {
        this.roomId = roomId;
    }

    public void sendMessage(TextMessage message, WebSocketSession excludeSession) {
        this.getSessions()
                .parallelStream()
                .filter(WebSocketSession::isOpen)
                .filter(session -> !session.equals(excludeSession))
                .forEach(session -> sendMessageToSession(session, message));
    }

    private void sendMessageToSession(WebSocketSession session, TextMessage message) {
        try {
            session.sendMessage(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void join(WebSocketSession session) {
        sessions.add(session);
    }

    public static LiveBidRoom of(String roomId) {
        return LiveBidRoom.builder()
                .roomId(roomId)
                .build();
    }
}
