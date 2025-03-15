package com.laborfarm.core_app.service.exception;

public class ExceptionMessage {

    private ExceptionMessage() {}

    //USER
    public static final String USER_NOT_FOUND = "User not found with the given ID.";
    public static final String USER_NOT_ACTIVE = "User exists but is not active.";
    public static final String USER_ALREADY_EXISTS = "User already exists.";

    //DEPARTMENT
    public static final String DEPARTMENT_ALREADY_EXISTS = "Department already exists.";
    public static final String DEPARTMENT_NOT_ACTIVE = "Department exists but is not active.";
    public static final String DEPARTMENT_NOT_FOUND = "Department not found with the given ID.";

    //PROJECT
    public static final String PROJECT_NOT_FOUND = "Project not found with the given ID.";
    public static final String PROJECT_ALREADY_EXISTS = "Project already exists.";
    public static final String PROJECT_NOT_ACTIVE = "Project exists but is not active.";

    //STATUS
    public static final String NO_MATCHING_STATUS = "No matching status found.";
}
