package com.laborfarm.core_app.service.exception.department;

import com.laborfarm.core_app.service.exception.ExceptionMessage;

public class DepartmentNotFoundException extends RuntimeException {
    public DepartmentNotFoundException() {
        super(ExceptionMessage.DEPARTMENT_NOT_FOUND);
    }

    public DepartmentNotFoundException(String message) {
        super(message);
    }
}
