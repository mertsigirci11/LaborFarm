package com.laborfarm.core_app.service.exception.project;

import com.laborfarm.core_app.service.exception.ExceptionMessage;

public class ProjectNotActiveException extends RuntimeException {
    public ProjectNotActiveException() {
        super(ExceptionMessage.PROJECT_NOT_ACTIVE);
    }

    public ProjectNotActiveException(String message) {
        super(message);
    }
}
