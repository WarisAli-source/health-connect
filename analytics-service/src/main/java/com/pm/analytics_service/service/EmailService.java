package com.pm.analytics_service.service;

import com.pm.analytics_service.kafka.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
    private static final Logger log = LoggerFactory.getLogger(
            EmailService.class);
    public void sendPatientCreatedEmail(String to, String name) {
        log.info("Sending Email....");

                SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Welcome to HealthConnect");
        message.setText("Dear " + name + ",\n\nYour patient profile has been successfully created in HealthConnect.\n\nRegards,\nTeam HealthConnect");

        mailSender.send(message);
    }
}
