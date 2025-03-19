package com.laborfarm.auth.entity.dto.role;

import lombok.Builder;

import java.util.UUID;

@Builder
public class RoleResponseDto {
    private UUID id;
    private UUID projectId;
    private UUID userId;
    private int roleId;
    private String roleName;
}