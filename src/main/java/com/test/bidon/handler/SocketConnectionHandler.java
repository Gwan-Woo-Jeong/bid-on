package com.test.bidon.handler;

import com.test.bidon.domain.Message;
import com.test.bidon.util.BidRoomUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.test.bidon.service.LiveBidService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SocketConnectionHandler extends TextWebSocketHandler {
    private final LiveBidService liveBidService;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();

        try {
            Message inMessage = BidRoomUtil.fromJson(payload, Message.class);
            liveBidService.handleAction(inMessage.getRoomId(), session, inMessage);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}