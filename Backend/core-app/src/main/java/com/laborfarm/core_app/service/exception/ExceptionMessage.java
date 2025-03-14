package com.laborfarm.core_app.service.exception;

public class ExceptionMessage {

    private ExceptionMessage() {}

    public static final String USER_NOT_FOUND = "User not found with the given ID.";
    public static final String USER_NOT_ACTIVE = "User exists but is not active.";
    public static final String USER_ALREADY_EXISTS = "User already exists.";
}
