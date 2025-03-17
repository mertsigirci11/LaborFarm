package com.laborfarm.core_app.service.exception.task;

import com.laborfarm.core_app.service.exception.ExceptionMessage;

public class StateCantSetAsBlockedException extends RuntimeException {
    public StateCantSetAsBlockedException() {
        super(ExceptionMessage.STATE_CANT_BE_BLOCKED);
    }

    public StateCantSetAsBlockedException(String message) {
        super(message);
    }
}
