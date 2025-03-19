package com.laborfarm.auth.exception;

public class EmailNotFoundException extends RuntimeException {
    public EmailNotFoundException() {
        super(ExceptionMessage.EMAIL_NOT_FOUND);
    }

    public EmailNotFoundException(String message) {
        super(message);
    }
}