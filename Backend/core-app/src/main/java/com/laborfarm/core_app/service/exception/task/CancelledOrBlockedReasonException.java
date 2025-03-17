package com.laborfarm.core_app.service.exception.task;

import com.laborfarm.core_app.service.exception.ExceptionMessage;

public class CancelledOrBlockedReasonException extends RuntimeException {
    public CancelledOrBlockedReasonException() {
        super(ExceptionMessage.REASON_IS_MANDATORY);
    }

    public CancelledOrBlockedReasonException(String message) {
        super(message);
    }
}
