package com.stocksimulator.example.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class websocketconfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config){
        // This is the prefix for the "channels" the frontend will listen to
        config.enableSimpleBroker("/topic");
        // This is the prefix for messages sent FROM frontend TO backend
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Add BOTH setAllowedOrigins to fix the CORS block
        registry.addEndpoint("/ws-market")
                .setAllowedOriginPatterns("*") // Allows any origin for testing
                .withSockJS();
    }

}
