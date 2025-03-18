package com.laborfarm.auth.entity.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CustomResponseDto<T> {
    private T data;
    private List<String> errors;
    private int statusCode;

    public static <T> CustomResponseDto<T> success(int statusCode, T data) {
        CustomResponseDto<T> response = new CustomResponseDto<T>();
        response.setData(data);
        response.setStatusCode(statusCode);
        return response;
    }

    public static <T> CustomResponseDto<T> success(int statusCode) {
        CustomResponseDto<T> response = new CustomResponseDto<T>();
        response.setStatusCode(statusCode);
        return response;
    }

    public static <T> CustomResponseDto<T> fail(int statusCode, List<String> errors) {
        CustomResponseDto<T> response = new CustomResponseDto<T>();
        response.setStatusCode(statusCode);
        response.setErrors(errors);
        return response;
    }

    public static <T> CustomResponseDto<T> fail(int statusCode, String error) {
        CustomResponseDto<T> response = new CustomResponseDto<T>();
        response.setStatusCode(statusCode);
        List<String> errors = new ArrayList<>();
        errors.add(error);
        response.setErrors(errors);
        return response;
    }
}