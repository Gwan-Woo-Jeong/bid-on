package com.test.bidon.handler;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.bidon.domain.Message;
import com.test.bidon.service.LiveBidService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SocketConnectionHandler extends TextWebSocketHandler {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final LiveBidService liveBidService;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();

        try {
            Message msg = objectMapper.readValue(payload, Message.class);

            System.out.println("msg = " + msg);

            liveBidService.handleAction(msg.getRoomId(), session, msg);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}