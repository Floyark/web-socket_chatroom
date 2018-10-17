package com.chatroom.config;

import com.chatroom.component.WsHandlerGroup;
import com.chatroom.handler.ChatRoomSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.Collection;
import java.util.Collections;

@Configuration
public class WebSocketUrlHandlerConfig implements WebSocketConfigurer {

    @Autowired
    private WsHandlerGroup wsHandlerGroup;
    private Collection<String> copyAttrs = Collections.singleton("user_name");
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(chatRoomHandler(), "/chatroom")
                .addInterceptors(new HttpSessionHandshakeInterceptor(copyAttrs));
    }

    @Bean
    WebSocketHandler chatRoomHandler() {
        return new ChatRoomSocketHandler();
    }
}
