package com.laborfarm.core_app.service.exception.task;

import com.laborfarm.core_app.service.exception.ExceptionMessage;

public class CompletedTaskException extends RuntimeException {
    public CompletedTaskException() {
        super(ExceptionMessage.TASK_COMPLETED);
    }

    public CompletedTaskException(String message) {
        super(message);
    }
}
