package com.laborfarm.core_app.service.exception.project;

import com.laborfarm.core_app.service.exception.ExceptionMessage;

public class ProjectAlreadyExists extends RuntimeException {
    public ProjectAlreadyExists(){
        super(ExceptionMessage.PROJECT_ALREADY_EXISTS);
    }

    public ProjectAlreadyExists(String message){
        super(message);
    }
}
