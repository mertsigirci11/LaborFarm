package com.laborfarm.core_app.service;

import com.laborfarm.core_app.entity.task.TaskComment;
import com.laborfarm.core_app.service.dto.CustomResponseDto;
import com.laborfarm.core_app.service.dto.TaskCommentRequestDto;
import com.laborfarm.core_app.service.dto.TaskCommentResponseDto;

import java.util.List;
import java.util.UUID;

public interface TaskCommentService {
    CustomResponseDto<TaskCommentResponseDto> addTaskComment(TaskCommentRequestDto taskCommentRequestDto);
    CustomResponseDto<TaskCommentResponseDto> updateTaskComment(TaskCommentRequestDto taskCommentRequestDto);
    CustomResponseDto<List<TaskCommentResponseDto>> getTaskCommentsByTaskId(UUID taskId);
    CustomResponseDto deleteTaskComment(UUID taskCommentId);
}