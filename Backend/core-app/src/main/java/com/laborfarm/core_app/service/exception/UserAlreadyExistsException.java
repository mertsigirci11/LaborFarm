package com.laborfarm.core_app.service.exception;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException() {
        super(ExceptionMessage.USER_ALREADY_EXISTS);
    }

    public UserAlreadyExistsException(String message) {
        super(message);
    }
}