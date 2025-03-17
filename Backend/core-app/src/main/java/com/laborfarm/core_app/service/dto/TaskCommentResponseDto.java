package com.laborfarm.core_app.service.dto;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class TaskCommentResponseDto {
    private UUID id;
    private UUID taskId;
    private UUID creatorUserId;
    private String comment;
    private String creatorUserFirstName;
    private String creatorUserLastName;
    private Date createdAt;
    private Date updatedAt;
}
