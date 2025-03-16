package com.laborfarm.core_app.service.dto;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class ProjectMemberResponseDto {
    private UUID id;
    private UUID projectId;
    private String projectName;
    private UUID userId;
    private Date createdAt;
    private Date updatedAt;
    private String userFirstName;
    private String userLastName;
}