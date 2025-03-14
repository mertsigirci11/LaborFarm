package com.laborfarm.core_app.service.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        super(ExceptionMessage.USER_NOT_FOUND);
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
