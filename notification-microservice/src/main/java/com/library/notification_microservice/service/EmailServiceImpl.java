package com.library.notification_microservice.service;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String emailFrom;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendEmail(String email) {
        try {
            MimeMessage mimeMailMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMailMessage, "utf-8");
            helper.setFrom(emailFrom);
            helper.setTo(email);
            helper.setSubject("Prestamo de libro");
            helper.setText("Estimado alumno, su prestamo fue efectuado con exito y recuerde devolverlo a tiempo o tendra una penalizacion cada dia pasada la fecha estimada.");
            mailSender.send(mimeMailMessage);

            log.info("Mensaje enviado a correo {}", email);
        }catch (MailException e) {
            System.err.println(e.getMessage());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void reminderLoan(List<String> emails) {
        emails.forEach(email -> {
            try {
                MimeMessage mimeMailMessage = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(mimeMailMessage, "utf-8");
                helper.setFrom(emailFrom);
                helper.setTo(email);
                helper.setSubject("Prestamo de libro");
                helper.setText("Se le recuerda la devolucion del libro ala biblioteca, pasada la fecha estimada se le añadira una penalizacion cada dia de 1.00$");
                mailSender.send(mimeMailMessage);

                log.info("Mensaje enviado a correo {}", email);
            }  catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
