package com.library.auth_microservice.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptions {

    @ExceptionHandler(PasswordNotMatchesException.class)
    public ResponseEntity<?> passwordNotMatches(PasswordNotMatchesException exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }
}
