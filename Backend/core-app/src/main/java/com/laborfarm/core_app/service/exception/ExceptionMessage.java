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
    public static final String USER_ALREADY_EXISTS_IN_PROJECT = "User already exists in the project.";
    public static final String PROJECT_MEMBER_NOT_FOUND = "Project member not found with the given ID.";

    //STATUS
    public static final String NO_MATCHING_STATUS = "No matching status found.";

    //STATE
    public static final String STATE_NOT_FOUND = "State not found with the given ID.";

    //PRIORITY
    public static final String PRIORITY_NOT_FOUND = "Priority not found with the given ID.";

    //TASK
    public static final String TASK_NOT_FOUND = "Task not found with the given ID.";
    public static final String TASK_COMPLETED = "Completed task's state can't be changed.";
    public static final String STATE_CANT_BE_BLOCKED = "State can't set as blocked if it is BACKLOG.";
    public static final String STATE_CANT_BE_CANCELLED = "State can't set as cancelled if it is COMPLETED.";
    public static final String REASON_IS_MANDATORY = "Cancelled or Blocked reason is mandatory";
    public static final String TASK_COMMENT_NOT_FOUND = "Task comment not found with the given ID.";
    public static final String FILE_NOT_FOUND = "File not found with the given ID.";
}