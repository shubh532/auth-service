package com.banking.auth_service.exception;

public class UserAlreadyExistException extends RuntimeException {

    public UserAlreadyExistException(String message){
        super(message);
    }
}
