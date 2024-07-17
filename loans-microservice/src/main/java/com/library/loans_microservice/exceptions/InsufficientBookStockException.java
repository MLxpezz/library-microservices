package com.library.loans_microservice.exceptions;

public class InsufficientBookStockException extends IllegalStateException{
    public InsufficientBookStockException(String message){
        super(message);
    }
}
