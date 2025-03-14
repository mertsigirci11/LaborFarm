package com.laborfarm.core_app.service.exception.user;

import com.laborfarm.core_app.service.exception.ExceptionMessage;

public class UserNotActiveException extends RuntimeException {

    public UserNotActiveException() {
        super(ExceptionMessage.USER_NOT_ACTIVE);
    }

    public UserNotActiveException(String message) {
        super(message);
    }
}
