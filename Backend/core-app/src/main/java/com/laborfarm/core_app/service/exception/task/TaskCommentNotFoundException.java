package com.laborfarm.core_app.service.exception.task;

import com.laborfarm.core_app.service.exception.ExceptionMessage;

public class TaskCommentNotFoundException extends RuntimeException {
    public TaskCommentNotFoundException() {
        super(ExceptionMessage.TASK_COMMENT_NOT_FOUND);
    }

    public TaskCommentNotFoundException(String message) {
        super(message);
    }
}
