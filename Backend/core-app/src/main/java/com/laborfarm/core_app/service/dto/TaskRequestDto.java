package com.laborfarm.core_app.service.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class TaskRequestDto {
    private UUID id;
    private String userStoryDescription;
    private String acceptanceCriteria;
    private String cancelledOrBlockedReason;
    private UUID assigneeId;
    private UUID projectId;
    private int stateId;
    private int priorityId;
}