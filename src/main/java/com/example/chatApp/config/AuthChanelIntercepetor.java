package com.example.chatApp.config;

import com.example.chatApp.serviceImp.UserServiceImp.JWTtoken;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

import java.security.Principal;

@Component
public class AuthChanelIntercepetor implements ChannelInterceptor {
    private final JWTtoken jwTtoken;

    public AuthChanelIntercepetor(JWTtoken jwTtoken) {
        this.jwTtoken = jwTtoken;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            String authHeader = accessor.getFirstNativeHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                throw new IllegalArgumentException("No JWT token found in Authorization header");
            }
            String token = authHeader.substring(7);
            String userName = jwTtoken.extractUserName(token);

            // Store your authenticated user ID
            Principal principal = () -> userName;
            accessor.setUser(principal);
        }

        return message;

    }
}
