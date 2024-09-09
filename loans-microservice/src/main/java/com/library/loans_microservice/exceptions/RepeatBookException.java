package com.library.loans_microservice.exceptions;

public class RepeatBookException extends IllegalStateException{
    public RepeatBookException(String message){
        super(message);
    }
}
