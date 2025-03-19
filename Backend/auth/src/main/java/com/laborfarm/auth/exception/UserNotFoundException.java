package com.laborfarm.auth.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super(ExceptionMessage.USER_NOT_FOUND);
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
