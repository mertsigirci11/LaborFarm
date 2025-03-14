package com.laborfarm.core_app.service.exception.department;

import com.laborfarm.core_app.service.exception.ExceptionMessage;

public class DepartmentAlreadyExistsException extends RuntimeException {
    public DepartmentAlreadyExistsException() {
        super(ExceptionMessage.DEPARTMENT_ALREADY_EXISTS);
    }

    public DepartmentAlreadyExistsException(String message) {
        super(message);
    }
}
