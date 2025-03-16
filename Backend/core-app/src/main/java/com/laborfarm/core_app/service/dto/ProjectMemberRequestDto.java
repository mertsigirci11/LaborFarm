package com.laborfarm.core_app.service.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Data
@Getter
@Setter
public class ProjectMemberRequestDto {
    private UUID id;
    private UUID projectId;
    private UUID userId;
}