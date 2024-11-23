package com.base.meta.service;

import com.base.meta.model.TestDefectComment;
import com.base.meta.repository.TestDefectRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    TestDefectRepository testDefectRepository;

    public void sendNotification(TestDefectComment testDefectComment) {
        List<Long> participants = testDefectRepository.findDevTesterOwnerAccountsByDefectId(testDefectComment.getTestDefect().getId());

        participants.forEach(participant -> {
            rabbitTemplate.convertAndSend("notificationQueue", testDefectComment);
        });
    }
}
