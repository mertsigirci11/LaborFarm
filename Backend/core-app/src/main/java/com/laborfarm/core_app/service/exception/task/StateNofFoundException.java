package com.laborfarm.core_app.service.exception.task;

public class StateNofFoundException extends RuntimeException {
    public StateNofFoundException() {
        super();
    }

    public StateNofFoundException(String message) {
        super(message);
    }
}
