package com.base.meta.service.fcmservice;

import com.base.meta.form.testdefectcomment.CreateTestDefectCommentForm;
import com.google.firebase.messaging.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
@Slf4j
public class FCMService {
    Logger logger = LoggerFactory.getLogger(FCMService.class);

    public void pushNotification(CreateTestDefectCommentForm request) throws InterruptedException, ExecutionException {
        try {
            sendMessage(request);
            logger.info("Message sent successfully");
        }
        catch (Exception e) {
            logger.error("Error sending message: " + e.getMessage());
        }
    }
    public void sendMessage(CreateTestDefectCommentForm request)
            throws InterruptedException, ExecutionException {
        Message message = getPreconfiguredMessageToToken(request);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonOutput = gson.toJson(message);
        String response = sendAndGetResponse(message);
        log.info("Sent message with data. Topic: " + request.getTitle() + ", " + response+ " msg "+jsonOutput);
        logger.info("Sent message with data. Topic: " + request.getTitle() + ", " + response+ " msg "+jsonOutput);
    }
    private String sendAndGetResponse(Message message) throws InterruptedException, ExecutionException {
        return FirebaseMessaging.getInstance().sendAsync(message).get();
    }


    private Message getPreconfiguredMessageToToken(CreateTestDefectCommentForm request) {
        return getPreconfiguredMessageBuilder(request).setToken(request.getSenderToken())
                .build();
    }

    private Message.Builder getPreconfiguredMessageBuilder(CreateTestDefectCommentForm request) {
        WebpushConfig webpushConfig = getWebpushConfig(request.getTitle(), request.getComment());

        Notification notification = Notification.builder()
                .setTitle(request.getTitle())
                .setBody(request.getComment())
                .build();

        return Message.builder()
                .setWebpushConfig(webpushConfig)
                .setNotification(notification);
    }

    private WebpushConfig getWebpushConfig(String title, String message) {
        WebpushNotification notification = WebpushNotification.builder()
                .setTitle(title)
                .setBody(message)
                .build();

        return WebpushConfig.builder()
                .setNotification(notification)
                .build();
    }
}
