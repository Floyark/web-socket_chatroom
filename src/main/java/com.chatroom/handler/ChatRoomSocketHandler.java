package com.chatroom.handler;

import com.chatroom.component.WsHandlerGroup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

import static com.chatroom.util.WsSessionUtil.getUserName;

@Slf4j
@Service
public class ChatRoomSocketHandler extends TextWebSocketHandler {

    @Autowired
    private WsHandlerGroup wsHandlerGroup;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        try {
            if (getUserName(session).isPresent()) {
                wsHandlerGroup.addHandlerAndSession(getUserName(session).get(), ChatRoomSocketHandler.this, session);
                sayWelcome(session);
                log.info("new user websocket is established");
            } else {
                throw new RuntimeException("null user_name field");
            }
        } catch (Exception e) {
            log.error("when connection established, occur errors", e);
            try {
                session.close(CloseStatus.NOT_ACCEPTABLE);
                log.info("session closed");
            } catch (IOException ioe) {
                log.error("session close error", ioe);
            }
        }
    }

    private void sayWelcome(WebSocketSession session) throws IOException {
        TextMessage payload = new TextMessage(String.format("%s, welcome you to join chat room!", getUserName(session).orElse("null")));
        session.sendMessage(payload);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        getUserName(session).ifPresent(useName -> {
            wsHandlerGroup.removeHandlerAndSession(useName, this, session);
        });
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        publishMessageToOthers(session, message);
    }

    private void publishMessageToOthers(WebSocketSession session, TextMessage message) {
        String msg = String.format("%s : %s", getUserName(session).orElse("null"), message.getPayload());
        TextMessage sendMsg = new TextMessage(msg);
        wsHandlerGroup.publishMsg(sendMsg);
    }
}
