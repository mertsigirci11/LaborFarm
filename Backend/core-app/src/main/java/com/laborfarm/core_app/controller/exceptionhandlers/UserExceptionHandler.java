package com.laborfarm.core_app.controller.exceptionhandlers;

import com.laborfarm.core_app.entity.User;
import com.laborfarm.core_app.service.dto.CustomResponseDto;
import com.laborfarm.core_app.service.exception.UserAlreadyExistsException;
import com.laborfarm.core_app.service.exception.UserNotActiveException;
import com.laborfarm.core_app.service.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler({
            UserNotFoundException.class,
            UserNotActiveException.class,
            UserAlreadyExistsException.class})
    @ResponseBody
    public ResponseEntity<CustomResponseDto<User>> handleException(Exception ex){
        CustomResponseDto<User> responseDto = new CustomResponseDto<>();
        responseDto.setStatusCode(HttpStatus.BAD_REQUEST.value());
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());
        responseDto.setErrors(errors);
        responseDto.setData(null);
        return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<CustomResponseDto<Object>> handleException(MethodArgumentNotValidException ex){
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .toList();

        CustomResponseDto<Object> responseDto = CustomResponseDto.fail(HttpStatus.BAD_REQUEST.value(), errors);

        return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
    }


}
