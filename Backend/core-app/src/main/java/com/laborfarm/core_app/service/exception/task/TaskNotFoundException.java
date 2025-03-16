package com.laborfarm.core_app.service.exception.task;

import com.laborfarm.core_app.service.exception.ExceptionMessage;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException() {
        super(ExceptionMessage.TASK_NOT_FOUND);
    }

    public TaskNotFoundException(String message) {
        super(message);
    }
}
