package com.laborfarm.core_app.service.exception.project;

import com.laborfarm.core_app.service.exception.ExceptionMessage;

public class ProjectNotFoundException extends RuntimeException {
    public ProjectNotFoundException() {
        super(ExceptionMessage.PROJECT_NOT_FOUND);
    }

    public ProjectNotFoundException(String message) {
        super(message);
    }
}
