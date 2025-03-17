package com.laborfarm.core_app.controller.exceptionhandlers;

import com.laborfarm.core_app.service.dto.CustomResponseDto;
import com.laborfarm.core_app.service.dto.ProjectResponseDto;
import com.laborfarm.core_app.service.exception.project.ProjectAlreadyExists;
import com.laborfarm.core_app.service.exception.project.ProjectNotActiveException;
import com.laborfarm.core_app.service.exception.project.ProjectNotFoundException;
import com.laborfarm.core_app.service.exception.status.NoMatchingStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ProjectExceptionHandler {

    @ExceptionHandler({
            ProjectNotActiveException.class,
            ProjectNotFoundException.class,
            ProjectAlreadyExists.class,
            NoMatchingStatusException.class})
    @ResponseBody
    public ResponseEntity<CustomResponseDto> handleException(Exception ex) {
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());

        return new ResponseEntity<>(CustomResponseDto.fail(HttpStatus.BAD_REQUEST.value(), errors), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<CustomResponseDto<Object>> handleException(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .toList();

        CustomResponseDto<Object> responseDto = CustomResponseDto.fail(HttpStatus.BAD_REQUEST.value(), errors);

        return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
    }
}