package com.base.meta.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
@Component
public class TestDefectCommentHandler extends TextWebSocketHandler {
    @Autowired
    SimpMessagingTemplate messagingTemplate;
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    RedisService redisService;

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws InterruptedException {
        // Tiếp nhận tin nhắn từ client
        String payload = message.getPayload();

        // Đưa tin nhắn vào RabbitMQ và Redis
        sendMessageToQueue(payload);

        // Thông báo tới tất cả các client qua WebSocket
        messagingTemplate.convertAndSend("/topic/test-defect-comment", payload);
    }

    private void sendMessageToQueue(String message) {
        // Gửi tin nhắn vào RabbitMQ để phân phối đến các thành viên khác
        // RabbitMQ queue sẽ phân phối tin nhắn đến các consumer
        rabbitTemplate.convertAndSend("commentQueue", message);
    }
}
