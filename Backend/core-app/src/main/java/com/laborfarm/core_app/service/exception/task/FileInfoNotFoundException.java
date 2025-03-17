package com.laborfarm.core_app.service.exception.task;

import com.laborfarm.core_app.service.exception.ExceptionMessage;

public class FileInfoNotFoundException extends RuntimeException {
    public FileInfoNotFoundException() {
        super(ExceptionMessage.FILE_NOT_FOUND);
    }

    public FileInfoNotFoundException(String message) {
        super(message);
    }
}
