package com.laborfarm.core_app.service.dto;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class FileInfoResponseDto {
    private UUID id;
    private UUID userId;
    private UUID taskId;
    private String fileName;
    private String location;
}