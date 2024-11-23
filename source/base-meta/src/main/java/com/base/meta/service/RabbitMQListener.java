package com.base.meta.service;

import com.base.meta.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQListener {
    @Autowired
    SimpMessagingTemplate messagingTemplate;

    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void receiveMessage(String message) {
        // Nhận tin nhắn từ RabbitMQ và gửi đến các client qua WebSocket
        messagingTemplate.convertAndSend("/topic/comments", message);
    }
}
