package com.laborfarm.core_app.service.implementation;

import com.laborfarm.core_app.entity.project.Project;
import com.laborfarm.core_app.entity.task.Task;
import com.laborfarm.core_app.entity.task.TaskComment;
import com.laborfarm.core_app.repository.TaskCommentRepository;
import com.laborfarm.core_app.service.TaskCommentService;
import com.laborfarm.core_app.service.dto.*;
import com.laborfarm.core_app.service.exception.task.TaskCommentNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class TaskCommentServiceImpl implements TaskCommentService {
    private final TaskCommentRepository taskCommentRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public TaskCommentServiceImpl(TaskCommentRepository taskCommentRepository, ModelMapper modelMapper) {
        this.taskCommentRepository = taskCommentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CustomResponseDto<TaskCommentResponseDto> addTaskComment(TaskCommentRequestDto taskCommentRequestDto) {
        TaskComment taskComment = convertToEntity(taskCommentRequestDto);
        taskComment.setCreatedAt(new Date());
        TaskComment savedTaskComment = taskCommentRepository.save(taskComment);
        TaskCommentResponseDto taskCommentResponseDto = convertToDto(savedTaskComment);

        return CustomResponseDto.success(HttpStatus.CREATED.value(), taskCommentResponseDto);
    }

    @Override
    public CustomResponseDto<TaskCommentResponseDto> updateTaskComment(TaskCommentRequestDto taskCommentRequestDto) {
        TaskComment taskComment = taskCommentRepository.findByIdAndIsActiveTrue(taskCommentRequestDto.getId());
        if (taskComment == null) {
            throw new TaskCommentNotFoundException();
        }

        taskComment.setUpdatedAt(new Date());
        taskComment.setComment(taskCommentRequestDto.getComment());
        taskComment.setTaskId(taskCommentRequestDto.getTaskId());
        taskComment.setCreatorUserId(taskCommentRequestDto.getCreatorUserId());

        TaskComment savedTaskComment = taskCommentRepository.save(taskComment);
        TaskCommentResponseDto taskCommentResponseDto = convertToDto(savedTaskComment);

        return CustomResponseDto.success(HttpStatus.OK.value(), taskCommentResponseDto);
    }

    @Override
    public CustomResponseDto<List<TaskCommentResponseDto>> getTaskCommentsByTaskId(UUID taskId) {
        List<TaskCommentResponseDto> response = taskCommentRepository.findByTaskIdAndIsActiveTrue(taskId)
                .stream()
                .map(this::convertToDto)
                .toList();

        return CustomResponseDto.success(HttpStatus.OK.value(), response);
    }

    @Override
    public CustomResponseDto deleteTaskComment(UUID taskCommentId) {
        TaskComment taskComment = taskCommentRepository.findByIdAndIsActiveTrue(taskCommentId);
        if (taskComment == null) {
            throw new TaskCommentNotFoundException();
        }
        taskComment.setActive(false);
        taskComment.setUpdatedAt(new Date());
        taskCommentRepository.save(taskComment);

        return CustomResponseDto.success(HttpStatus.NO_CONTENT.value());
    }

    //Helper methods
    public TaskCommentResponseDto convertToDto(TaskComment taskComment) {
        return modelMapper.map(taskComment, TaskCommentResponseDto.class);
    }

    public TaskComment convertToEntity(TaskCommentRequestDto taskCommentRequestDto) {
        return modelMapper.map(taskCommentRequestDto, TaskComment.class);
    }
}
