package com.laborfarm.core_app.service.exception.task;

import com.laborfarm.core_app.service.exception.ExceptionMessage;

public class PriorityNotFoundException extends RuntimeException {
    public PriorityNotFoundException() {
        super(ExceptionMessage.PRIORITY_NOT_FOUND);
    }

    public PriorityNotFoundException(String message) {
        super(message);
    }
}
