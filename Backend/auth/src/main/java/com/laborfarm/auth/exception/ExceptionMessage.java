package com.laborfarm.auth.exception;

public class ExceptionMessage {

    private ExceptionMessage(){}

    public static final String EMAIL_NOT_FOUND = "Email not found.";
    public static final String EMAIL_ALREADY_EXISTS = "Email already exists.";
    public static final String USER_NOT_FOUND = "User not found with the given ID.";
    public static final String ROLE_NOT_FOUND = "Role not found with the given ID.";
}