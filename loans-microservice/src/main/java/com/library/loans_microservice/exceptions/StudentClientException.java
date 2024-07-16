package com.library.loans_microservice.exceptions;

public class StudentClientException extends RuntimeException{

    public StudentClientException(String message) {
        super(message);
    }
}
