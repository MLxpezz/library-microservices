package com.library.loans_microservice.exceptions;

public class BookClientException extends RuntimeException{

    public BookClientException(String message) {
        super(message);
    }
}
