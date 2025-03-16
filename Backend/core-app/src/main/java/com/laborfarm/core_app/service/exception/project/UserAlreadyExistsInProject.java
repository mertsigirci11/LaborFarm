package com.laborfarm.core_app.service.exception.project;

import com.laborfarm.core_app.service.exception.ExceptionMessage;

public class UserAlreadyExistsInProject extends RuntimeException {
    public UserAlreadyExistsInProject(){
        super(ExceptionMessage.USER_ALREADY_EXISTS_IN_PROJECT);
    }

    public UserAlreadyExistsInProject(String message){
        super(message);
    }
}
