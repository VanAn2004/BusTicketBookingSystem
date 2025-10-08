package com.busticket.busticket_bookingsystem.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // ✅ Endpoint để client kết nối vào WebSocket
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*")  // Cho phép mọi domain
                .withSockJS(); // Hỗ trợ fallback cho browser cũ không có WebSocket
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // ✅ Prefix khi client gửi message đến server
        config.setApplicationDestinationPrefixes("/app");
        // ✅ Prefix khi server broadcast message lại cho client
        config.enableSimpleBroker("/topic");
    }
}
