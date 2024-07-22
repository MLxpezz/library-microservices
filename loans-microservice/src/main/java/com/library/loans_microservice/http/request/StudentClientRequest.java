package com.library.loans_microservice.http.request;

import com.library.loans_microservice.dto.StudentDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PutExchange;

@HttpExchange
public interface StudentClientRequest {

    @GetExchange(url = "/get/{id}")
    public StudentDTO getStudentById(@PathVariable Long id);

    @PutExchange("/student-loan/{id}")
    public StudentDTO studentLoan(@PathVariable Long id);

    @PutExchange("/student-return-book/{id}")
    public StudentDTO studentReturnook(@PathVariable Long id);
}
