package com.chatroom.config;

import com.chatroom.handler.ChatRoomSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

@Configuration
public class WebSocketUrlHandlerConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(chatRoomHandler(), "/chatroom")
                .addInterceptors(new HttpSessionHandshakeInterceptor());
    }

    @Bean
    WebSocketHandler chatRoomHandler() {
        return new ChatRoomSocketHandler();
    }
}
