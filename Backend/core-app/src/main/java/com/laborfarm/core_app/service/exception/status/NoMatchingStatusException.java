package com.laborfarm.core_app.service.exception.status;

import com.laborfarm.core_app.service.exception.ExceptionMessage;

public class NoMatchingStatusException extends RuntimeException {
    public NoMatchingStatusException() {
        super(ExceptionMessage.NO_MATCHING_STATUS);
    }

    public NoMatchingStatusException(String message) {
        super(message);
    }
}
