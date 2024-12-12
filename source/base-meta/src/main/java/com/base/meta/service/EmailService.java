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
    private JavaMailSender emailSender;
    @Value("${spring.mail.username}")
    private String from;

    public void sendEmail(String to, String subject, String text) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text, true);
        helper.setFrom(from);

        emailSender.send(message);
    }

    public void sendOtpEmail(String to, String otp, int validDuration) throws MessagingException {
        String subject = "Your OTP Code for Password Reset";
        String text = String.format(
                "<html><body>" +
                        "<h2>Reset Your Password</h2>" +
                        "<p>Dear user %s,</p>" +
                        "<p>We received a request to reset the password for your account. Use the OTP code below to reset your password:</p>" +
                        "<h3 style='color:blue;'>%s</h3>" +
                        "<p>This code is valid for <strong>%d hours</strong>. Please use it promptly.</p>" +
                        "<p>If you did not request a password reset, please ignore this email. No changes have been made to your account.</p>" +
                        "<p>Thank you for your contribution.</p>" +
                        "<p>Best regards,<br>Double 2K</p>" +
                        "</body></html>", to, otp, validDuration);

        sendEmail(to, subject, text);
    }

    public void sendPasswordEmail(String to, String fullName, String username, String password) throws MessagingException {
        String subject = "Your Account Information";
        String text = String.format(
                "<html><body>" +
                        "<h2>Your Account Information</h2>" +
                        "<p>Dear %s,</p>" +
                        "<p>Your account has been created successfully. Below are your account details:</p>" +
                        "<p><strong>Username:</strong> %s</p>" +
                        "<p><strong>Password:</strong> %s</p>" +
                        "<p>Please keep this information confidential and do not share it with anyone.</p>" +
                        "<p>Thank you for your contribution.</p>" +
                        "<p>Best regards,<br>Double 2K</p>" +
                        "</body></html>", fullName, username, password);

        sendEmail(to, subject, text);
    }
}
