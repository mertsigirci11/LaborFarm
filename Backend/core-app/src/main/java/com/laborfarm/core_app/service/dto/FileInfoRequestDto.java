package com.laborfarm.core_app.service.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

@Data
public class FileInfoRequestDto {
    private UUID id;
    private UUID userId;
    private UUID taskId;
}