package com.laborfarm.core_app.service;

import com.laborfarm.core_app.service.dto.CustomResponseDto;
import com.laborfarm.core_app.service.dto.TaskRequestDto;
import com.laborfarm.core_app.service.dto.TaskResponseDto;

import java.util.List;
import java.util.UUID;

public interface TaskService {
    CustomResponseDto<TaskResponseDto> addTask(TaskRequestDto taskRequestDto);
    CustomResponseDto<TaskResponseDto> updateTask(TaskRequestDto taskRequestDto);
    CustomResponseDto deleteTask(UUID taskId);
    CustomResponseDto<TaskResponseDto> getTaskById(UUID taskId);
    CustomResponseDto<List<TaskResponseDto>> getAllTasks();
    CustomResponseDto<List<TaskResponseDto>> getAllTasksByStateId(int stateId);
    CustomResponseDto<List<TaskResponseDto>> getAllTasksByPriorityId(int priorityId);
    CustomResponseDto<List<TaskResponseDto>> getProjectTasks(UUID projectId);
    CustomResponseDto<List<TaskResponseDto>> getUserTasks(UUID userId);
}