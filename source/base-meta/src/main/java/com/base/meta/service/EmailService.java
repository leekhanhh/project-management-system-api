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
                        "<p>This code is valid for <strong>%d hour</strong>. Please use it promptly.</p>" +
                        "<p>If you did not request a password reset, please ignore this email. No changes have been made to your account.</p>" +
                        "<p>Thank you for your contribution.</p>" +
                        "<p>Best regards,<br>Double K Team</p>" +
                        "</body></html>", otp, validDuration);

        sendEmail(to, subject, text);
    }

    public void sendPasswordEmail(String to, String fullName, String username, String password) throws MessagingException {
        String subject = "Your Account Password";
        String text = String.format(
                "<html><body>" +
                        "<h2>Your New Password</h2>" +
                        "<p>Dear <strong>%s</strong>,</p>" +
                        "<p>We have successfully generated a new password for your account. Please find the details below:</p>" +
                        "<p><strong>Username:</strong> %s</p>" +
                        "<p><strong>Password:</strong> <span style='color:blue;'>%s</span></p>" +
                        "<p>For your security, we recommend that you log in immediately and change your password to something only you will know.</p>" +
                        "<p>If you did not request this password reset or need assistance, please contact our support team at <a href='lyhongkhanh.it@gmail.com'>lyhongkhanh.it@gmail.com</a>.</p>" +
                        "<p>Thank you for your contribution.</p>" +
                        "<p>Best regards,<br>Double2K</p>" +
                        "</body></html>", fullName, username, password);

        sendEmail(to, subject, text);
    }

}
