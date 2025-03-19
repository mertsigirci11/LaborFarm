package com.laborfarm.auth.entity.dto.role;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class RoleResponseDto {
    private UUID id;
    private UUID projectId;
    private UUID userId;
    private int roleId;
    private String roleName;
}