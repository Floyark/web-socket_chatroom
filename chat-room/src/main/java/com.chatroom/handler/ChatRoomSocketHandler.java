package com.chatroom.handler;

import io.netty.util.internal.ConcurrentSet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
public class ChatRoomSocketHandler extends TextWebSocketHandler {
    private ConcurrentSet<WebSocketSession> userSessions = new ConcurrentSet();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        userSessions.add(session);
        log.info("new user websocket is established");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        userSessions.remove(session);
        log.info("a user connection closed, status = {}", status);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.info("sessionId = {} said: {} ", session.getId(), message);
        String payload = message.getPayload();
        if (!payload.isEmpty() && payload.toLowerCase().startsWith("i am ")) {
            String name = payload.substring(payload.lastIndexOf(" ") + 1);
            session.sendMessage(new TextMessage("Welcome " + name));
        }
    }
}
