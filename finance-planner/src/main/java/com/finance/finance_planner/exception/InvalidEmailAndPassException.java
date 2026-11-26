package com.finance.finance_planner.exception;

public class InvalidEmailAndPassException extends RuntimeException{
    public InvalidEmailAndPassException(String message){
        super(message);
    }
}
