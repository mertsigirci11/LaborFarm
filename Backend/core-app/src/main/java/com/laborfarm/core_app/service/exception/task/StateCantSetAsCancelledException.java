package com.laborfarm.core_app.service.exception.task;

import com.laborfarm.core_app.service.exception.ExceptionMessage;

public class StateCantSetAsCancelledException extends RuntimeException {
    public StateCantSetAsCancelledException() {
        super(ExceptionMessage.STATE_CANT_BE_CANCELLED);
    }

    public StateCantSetAsCancelledException(String message) {
        super(message);
    }
}
