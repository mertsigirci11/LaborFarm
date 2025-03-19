package com.laborfarm.auth.exception;

public class RoleNotFoundException extends RuntimeException {
    public RoleNotFoundException() {
        super(ExceptionMessage.ROLE_NOT_FOUND);
    }

    public RoleNotFoundException(String message) {
        super(message);
    }
}
