package com.base.meta.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;

@Service
@Slf4j
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String from;
    @Value("${spring.mail.password}")
    private String password;

    public void sendEmail(String to, String subject, String text) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text, true);
        helper.setFrom(from);

        javaMailSender.send(message);
    }
    public void sendOtpEmail(String to, String otp, int validDuration) throws MessagingException {
        String subject = "Your OTP Code for Password Reset";
        String text = String.format(
                "<html><body>" +
                        "<h2>Reset Your Password</h2>" +
                        "<p>Dear user,</p>" +
                        "<p>We received a request to reset the password for your account. Use the OTP code below to reset your password:</p>" +
                        "<h3 style='color:blue;'>%s</h3>" +
                        "<p>This code is valid for <strong>%d minutes</strong>. Please use it promptly.</p>" +
                        "<p>If you did not request a password reset, please ignore this email. No changes have been made to your account.</p>" +
                        "<p>Thank you for using our movie streaming service.</p>" +
                        "<p>Best regards,<br>Movie Streaming Team</p>" +
                        "</body></html>", otp, validDuration);

        sendEmail(to, subject, text);
    }
}
