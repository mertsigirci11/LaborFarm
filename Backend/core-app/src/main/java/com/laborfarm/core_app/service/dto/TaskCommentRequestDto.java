package com.laborfarm.core_app.service.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.UUID;

@Data
public class TaskCommentRequestDto {
    private UUID id;
    private UUID taskId;
    private UUID creatorUserId;

    @NotEmpty(message = "Comment can't be null.")
    private String comment;
}
