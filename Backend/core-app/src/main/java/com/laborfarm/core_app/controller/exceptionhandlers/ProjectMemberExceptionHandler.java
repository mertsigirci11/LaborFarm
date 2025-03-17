package com.laborfarm.core_app.controller.exceptionhandlers;

import com.laborfarm.core_app.service.dto.CustomResponseDto;
import com.laborfarm.core_app.service.dto.ProjectMemberResponseDto;
import com.laborfarm.core_app.service.dto.ProjectResponseDto;
import com.laborfarm.core_app.service.exception.project.ProjectMemberNotFound;
import com.laborfarm.core_app.service.exception.project.ProjectNotFoundException;
import com.laborfarm.core_app.service.exception.project.UserAlreadyExistsInProject;
import com.laborfarm.core_app.service.exception.user.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ProjectMemberExceptionHandler {

    @ExceptionHandler({
            ProjectMemberNotFound.class,
            UserAlreadyExistsInProject.class,
            UserNotFoundException.class,
            ProjectNotFoundException.class
    })
    @ResponseBody
    public ResponseEntity<CustomResponseDto<ProjectMemberResponseDto>> handleException(Exception ex){
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());

        return new ResponseEntity<>(CustomResponseDto.fail(HttpStatus.BAD_REQUEST.value(), errors), HttpStatus.BAD_REQUEST);
    }
}
