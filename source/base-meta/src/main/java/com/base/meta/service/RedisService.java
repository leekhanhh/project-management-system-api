package com.base.meta.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RedisService {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    private static final String COMMENT_MESSAGES_KEY = "comment_messages";

    public void saveMessageToRedis(String message) {
        redisTemplate.opsForList().leftPush(COMMENT_MESSAGES_KEY, message);
    }

    public List<String> getChatMessages() {
        return redisTemplate.opsForList().range(COMMENT_MESSAGES_KEY, 0, -1);
    }
}
