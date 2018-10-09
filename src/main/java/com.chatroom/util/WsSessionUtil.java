package com.chatroom.util;

import com.chatroom.constant.WsConst;
import org.springframework.web.socket.WebSocketSession;

import java.util.Optional;

public class WsSessionUtil {

    public static Optional<String> getUserName(WebSocketSession session) {
        final String field = WsConst.USER_NAME;
        String attr = null;
        Optional<String> query = Optional.ofNullable(session.getUri().getQuery());
        int userNameIndex = query.orElse("").indexOf(field);
        if (userNameIndex >= 0) {
            int endIndex = query.get().indexOf("&", userNameIndex);
            if (endIndex < 0) {
                attr = query.get().substring(userNameIndex + field.length());
            } else {
                attr = query.get().substring(userNameIndex + field.length(), endIndex);
            }
        }
        return Optional.ofNullable(attr);
    }
}
