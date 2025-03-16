package com.laborfarm.core_app.service.exception.project;

import com.laborfarm.core_app.service.exception.ExceptionMessage;

public class ProjectMemberNotFound extends RuntimeException {
    public ProjectMemberNotFound() {
        super(ExceptionMessage.PROJECT_MEMBER_NOT_FOUND);
    }

    public ProjectMemberNotFound(String message) {
        super(message);
    }
}
