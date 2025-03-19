package com.laborfarm.auth.entity.dto.role;

import lombok.Data;

import java.util.UUID;

@Data
public class RoleRequestDto {
    private UUID id;
    private UUID projectId;
    private UUID userId;
    private int roleId;
}