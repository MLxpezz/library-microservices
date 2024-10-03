package com.library.notification_microservice.listeners;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.notification_microservice.service.EmailService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class LoanListener {

    private final EmailService emailservice;

    public LoanListener(EmailService emailservice) {
        this.emailservice = emailservice;
    }

    @KafkaListener(topics = "loanNotification")
    public void listen(String email) {
        if(email != null || email.isEmpty()) {
            emailservice.sendEmail(email);
        }
    }

    @KafkaListener(topics = "reminderLoans")
    public void reminder(String emailsJson) throws IOException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            List<String> emails = objectMapper.readValue(emailsJson, new TypeReference<List<String>>() {});
            emailservice.reminderLoan(emails);
        } catch (Exception e) {
            // Maneja la excepción de deserialización
            System.err.println("Error deserializing emails JSON: " + e.getMessage());
        }
    }
}
