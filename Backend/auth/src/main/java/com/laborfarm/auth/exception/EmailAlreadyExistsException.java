package com.laborfarm.auth.exception;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException() {
        super(ExceptionMessage.EMAIL_ALREADY_EXISTS);
    }

    public EmailAlreadyExistsException(String message) {
        super(message);
    }
}
