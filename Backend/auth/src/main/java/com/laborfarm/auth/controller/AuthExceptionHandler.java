package com.laborfarm.auth.controller;

import com.laborfarm.auth.entity.dto.CustomResponseDto;
import com.laborfarm.auth.exception.EmailAlreadyExistsException;
import com.laborfarm.auth.exception.EmailNotFoundException;
import com.laborfarm.auth.exception.RoleNotFoundException;
import com.laborfarm.auth.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class AuthExceptionHandler {

    @ExceptionHandler({
            EmailAlreadyExistsException.class,
            EmailNotFoundException.class,
            RoleNotFoundException.class,
            UserNotFoundException.class})
    @ResponseBody
    public ResponseEntity<CustomResponseDto> handleException(Exception ex) {
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());

        return new ResponseEntity<>(CustomResponseDto.fail(HttpStatus.BAD_REQUEST.value(), errors), HttpStatus.BAD_REQUEST);
    }
}
