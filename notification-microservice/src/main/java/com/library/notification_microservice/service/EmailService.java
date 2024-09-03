package com.library.notification_microservice.service;

import java.util.List;

public interface EmailService {

    void sendEmail(String email);
    void reminderLoan(List<String> emails);
}
