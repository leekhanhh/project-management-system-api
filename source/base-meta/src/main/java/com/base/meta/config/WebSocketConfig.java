package com.base.meta.config;

import com.base.meta.service.TestDefectCommentHandler;
import com.base.meta.service.WebSocketHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Slf4j
@Configuration
@EnableWebSocketMessageBroker
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer, WebSocketMessageBrokerConfigurer {
    @Autowired
    TestDefectCommentHandler testDefectCommentHandler;
    @Autowired
    WebSocketHandler webSocketHandler;
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        if (testDefectCommentHandler != null) {
            log.info("Registering TestDefectCommentHandler...");
        }
        if (webSocketHandler != null) {
            log.info("Registering WebSocketHandler...");
        }
        webSocketHandlerRegistry.addHandler(testDefectCommentHandler, "/ws")
                .setAllowedOrigins("*").withSockJS();
        webSocketHandlerRegistry.addHandler(webSocketHandler, "/test-defect-comment")
                .setAllowedOrigins("*").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").withSockJS();
    }
}
