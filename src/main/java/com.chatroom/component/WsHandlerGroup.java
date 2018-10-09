package com.chatroom.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentSkipListMap;

@Component
@Slf4j
public class WsHandlerGroup {
    private Integer userLimit = 15;
    private ConcurrentSkipListMap<String, WebSocketHandler> uniquUserNameHandlers = new ConcurrentSkipListMap();
    private ArrayList<WebSocketSession> userSessions = new ArrayList<>();

    public void addHandlerAndSession(String userName, WebSocketHandler handler, WebSocketSession session) {
        if (isFull()) {
            throw new RuntimeException("Ws socket number limit");
        } else if (isUnique(userName)) {
            uniquUserNameHandlers.put(userName, handler);
            userSessions.add(session);
            log.debug("add handler and session, username={}", userName);
        } else {
            throw new RuntimeException("duplicated user_name");
        }
    }

    private boolean isFull() {
        return uniquUserNameHandlers.size() >= userLimit;
    }

    public void removeHandlerAndSession(String userName, WebSocketHandler webSocketHandler, WebSocketSession session) {
        if (StringUtils.isEmpty(userName)) {
        } else {
            uniquUserNameHandlers.remove(userName, webSocketHandler);
            userSessions.remove(session);
            log.debug("remove handler and session, username={}", userName);
        }
    }

    public boolean isUnique(String userName) {
        if (StringUtils.isEmpty(userName)) {
            return false;
        }
        return !uniquUserNameHandlers.containsKey(userName);
    }

    public void publishMsg(TextMessage sendMsg) {
        userSessions.parallelStream().forEach(session -> {
            try {
                session.sendMessage(sendMsg);
            } catch (IOException e) {
                log.error("send message error", e);
            }
        });
    }
}
