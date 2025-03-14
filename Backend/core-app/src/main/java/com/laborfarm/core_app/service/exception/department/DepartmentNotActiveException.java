package com.laborfarm.core_app.service.exception.department;

import com.laborfarm.core_app.service.exception.ExceptionMessage;

public class DepartmentNotActiveException extends RuntimeException {
    public DepartmentNotActiveException() {
        super(ExceptionMessage.DEPARTMENT_NOT_ACTIVE);
    }

    public DepartmentNotActiveException(String message) {
        super(message);
    }
}
