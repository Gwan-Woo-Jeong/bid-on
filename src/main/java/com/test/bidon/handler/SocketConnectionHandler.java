package com.test.bidon.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.bidon.domain.Message;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.web.socket.WebSocketSession;

public class SocketConnectionHandler extends TextWebSocketHandler {

	private static List<WebSocketSession> sessionList;

	static {
		sessionList = Collections.synchronizedList(new ArrayList<>());
	}

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);

        System.out.println("[LOG] 클라이언트가 접속했습니다.");

        sessionList.add(session);

        System.out.println(sessionList.size());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);

        System.out.println("[LOG] 클라이언트가 접속 종료했습니다.");

        System.out.println(session);

        sessionList.remove(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        ObjectMapper mapper = new ObjectMapper();

        Message msg = mapper.readValue(message.getPayload(), Message.class);

        System.out.println(msg);

        switch (msg.getCode()) {

            //새로운 유저 접속
            case "1" -> {
                for (WebSocketSession s : sessionList) {
                    if (s != session) {
                        try {
                            s.sendMessage(new TextMessage(mapper.writeValueAsString(msg)));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }

            //기존 유저 나감
            case "2" -> {
                sessionList.remove(session);

                for (WebSocketSession s : sessionList) {
                    try {
                        s.sendMessage(new TextMessage(mapper.writeValueAsString(msg)));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            //클라이언트가 보낸 메시지를 다른 클라이언트에게 전달
            case "3" -> {
                for (WebSocketSession s : sessionList) {
                    if (s != session) {
                        try {
                            s.sendMessage(new TextMessage(mapper.writeValueAsString(msg)));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        super.handleTransportError(session, exception);
        System.out.println("[LOG] 에러가 발생했습니다. " + exception.getMessage());
    }

}
