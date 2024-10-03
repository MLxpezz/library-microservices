package com.library.loans_microservice.schedule;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.loans_microservice.entity.LoanEntity;
import com.library.loans_microservice.http.request.AuthClientRequest;
import com.library.loans_microservice.http.request.StudentClientRequest;
import com.library.loans_microservice.repository.LoanRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class ScheduleReminder {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final LoanRepository loanRepository;
    private final StudentClientRequest studentClientRequest;
    private final AuthClientRequest authClientRequest;

    public ScheduleReminder(LoanRepository loanRepository, KafkaTemplate<String, String> kafkaTemplate, StudentClientRequest studentClientRequest, AuthClientRequest authClientRequest) {
        this.loanRepository = loanRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.studentClientRequest = studentClientRequest;
        this.authClientRequest = authClientRequest;
    }

    @Scheduled(cron = "@daily")
    public void reminderLoan() throws JsonProcessingException {

        //generar token interno para acceder a los estudiantes
        String token = authClientRequest.getToken();

        //verificar las fechas de devolucion de los prestamos
        List<LoanEntity> loanExpired = loanRepository.findAll()
                .stream()
                .filter(loan -> {
                    LocalDate returnDate = loan.getReturnDate();
                    LocalDate now = LocalDate.now();
                    LocalDate nowPlusTwoDays = LocalDate.now().plusDays(2);

                    // Retorna true si la fecha de devolución está entre hoy y dentro de dos días
                    return (returnDate.isAfter(now) || returnDate.isEqual(now)) && returnDate.isBefore(nowPlusTwoDays);
                }).toList();


        //obtener los emails de los alumnos con fechas de prestamo casi vencidas
        List<String> studentEmails = loanRepository.findAll()
                .stream()
                .map(loan -> studentClientRequest.getStudentById(loan.getStudentId(), token).email()).toList();

        ObjectMapper objectMapper = new ObjectMapper();
        String emailsJson = objectMapper.writeValueAsString(studentEmails);
        kafkaTemplate.send("reminderLoans" , emailsJson);
    }
}
