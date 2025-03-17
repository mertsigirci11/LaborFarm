package com.laborfarm.core_app.service.dto;

import lombok.Data;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Data
public class TaskResponseDto {
    private UUID id;
    private String title;
    private String userStoryDescription;
    private String acceptanceCriteria;
    private String cancelledOrBlockedReason;
    //Assignee
    private UUID assigneeId;
    private String assigneeFirstName;
    private String assigneeLastName;
    //Project
    private UUID projectId;
    private String projectName;
    //State
    private int stateId;
    private String stateName;
    //Priority
    private int priorityId;
    private String priorityName;
    //Date
    private Date createdAt;
    private Date updatedAt;
}