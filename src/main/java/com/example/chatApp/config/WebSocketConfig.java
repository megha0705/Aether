package com.example.chatApp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig  implements WebSocketMessageBrokerConfigurer {
    private final AuthChanelIntercepetor authChanelIntercepetor;

    public WebSocketConfig(AuthChanelIntercepetor authChanelIntercepetor) {
        this.authChanelIntercepetor = authChanelIntercepetor;
    }

    @Override
public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry.addEndpoint("/chat").setAllowedOriginPatterns("*") .withSockJS();
}
@Override
public void configureMessageBroker(MessageBrokerRegistry registry) {
    registry.enableSimpleBroker("/topic"); // Enables broadcasting to subscribers
    registry.setApplicationDestinationPrefixes("/app"); // Prefix for client to send messages
}

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(authChanelIntercepetor);
    }
}
