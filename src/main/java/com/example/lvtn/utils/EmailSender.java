package com.example.lvtn.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSender {
    @Autowired
    public JavaMailSender emailSender;

    public void sendEmail(String email, String subject, String content) throws InternalException {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject(subject);
            message.setText(content);
            // Send Email!
            this.emailSender.send(message);
        } catch (Exception e){
            e.printStackTrace();
            throw new InternalException("Send email error: " + e.getMessage());
        }
    }
}
