package com.laborfarm.core_app.service.exception.task;

import com.laborfarm.core_app.service.exception.ExceptionMessage;

public class StateNotFoundException extends RuntimeException {
    public StateNotFoundException() {
        super(ExceptionMessage.STATE_NOT_FOUND);
    }

    public StateNotFoundException(String message) {
        super(message);
    }
}
