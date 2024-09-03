package com.library.loans_microservice.http.request;

import com.library.loans_microservice.dto.StudentDTO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PutExchange;

@HttpExchange
public interface StudentClientRequest {

    @GetExchange(url = "/get/{id}")
    StudentDTO getStudentById(@PathVariable Long id);

    @GetExchange(url = "/get/{id}")
    StudentDTO getStudentById(@PathVariable Long id, @RequestHeader("Authorization") String token);

    @PutExchange("/student-loan/{id}")
    StudentDTO studentLoan(@PathVariable Long id);

    @PutExchange("/student-return-book/{id}")
    StudentDTO studentReturnook(@PathVariable Long id);
}
