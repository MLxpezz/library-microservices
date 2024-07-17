package com.library.loans_microservice.exceptions;

public class MaxLoansReachedException extends IllegalStateException{
    public MaxLoansReachedException(String message){
        super(message);
    }
}
