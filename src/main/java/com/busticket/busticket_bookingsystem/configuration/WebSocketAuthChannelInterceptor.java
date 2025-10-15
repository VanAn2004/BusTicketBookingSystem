package com.busticket.busticket_bookingsystem.configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.stereotype.Component;
import com.busticket.busticket_bookingsystem.configuration.CustomJwtDecoder;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketAuthChannelInterceptor implements ChannelInterceptor {

    private final CustomJwtDecoder customJwtDecoder;
    private final JwtAuthenticationConverter jwtAuthenticationConverter;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor =
                MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            String jwtToken = null;

            //  1. Lấy token từ header Authorization
            String authorizationHeader = accessor.getFirstNativeHeader("Authorization");
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                jwtToken = authorizationHeader.substring(7);
            }

            //  2. Nếu không có, thử lấy token từ query param (?token=...)
            if (jwtToken == null) {
                try {
                    String simpConnectMessage = accessor.toNativeHeaderMap().toString();
                    if (simpConnectMessage.contains("token=")) {
                        jwtToken = simpConnectMessage.substring(simpConnectMessage.indexOf("token=") + 6);
                        int amp = jwtToken.indexOf('&');
                        if (amp != -1) jwtToken = jwtToken.substring(0, amp);
                    }
                } catch (Exception e) {
                    log.warn("Không thể parse token từ query param: {}", e.getMessage());
                }
            }

            if (jwtToken != null) {
                try {
                    Jwt jwt = customJwtDecoder.decode(jwtToken);
                    Authentication authentication = jwtAuthenticationConverter.convert(jwt);
                    accessor.setUser(authentication);
                    log.info(" WebSocket authenticated user: {}", authentication.getName());
                } catch (Exception e) {
                    log.error("Invalid WebSocket JWT: {}", e.getMessage());
                    accessor.setUser(null);
                    return null;
                }
            } else {
                log.warn("Missing token in WebSocket CONNECT");
                accessor.setUser(null);
                return null;
            }
        }

        return message;
    }
}
