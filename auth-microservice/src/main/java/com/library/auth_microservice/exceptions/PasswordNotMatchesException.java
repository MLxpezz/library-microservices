package com.library.auth_microservice.exceptions;

public class PasswordNotMatchesException extends RuntimeException{
    public PasswordNotMatchesException(String message){
        super(message);
    }

}
