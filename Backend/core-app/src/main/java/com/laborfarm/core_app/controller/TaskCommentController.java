package com.laborfarm.core_app.controller;

import com.laborfarm.core_app.service.TaskCommentService;
import com.laborfarm.core_app.service.dto.CustomResponseDto;
import com.laborfarm.core_app.service.dto.TaskCommentRequestDto;
import com.laborfarm.core_app.service.dto.TaskCommentResponseDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/taskcomments")
public class TaskCommentController {
    private final TaskCommentService taskCommentService;

    @Autowired
    public TaskCommentController(TaskCommentService taskCommentService) {
        this.taskCommentService = taskCommentService;
    }

    @PostMapping("/v1")
    public ResponseEntity<CustomResponseDto<TaskCommentResponseDto>> addTaskComment(@RequestBody @Valid TaskCommentRequestDto taskCommentRequestDto) {
        CustomResponseDto<TaskCommentResponseDto> response = taskCommentService.addTaskComment(taskCommentRequestDto);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @PutMapping("/v1")
    public ResponseEntity<CustomResponseDto<TaskCommentResponseDto>> updateTaskComment(@RequestBody @Valid TaskCommentRequestDto taskCommentRequestDto) {
        CustomResponseDto<TaskCommentResponseDto> response = taskCommentService.updateTaskComment(taskCommentRequestDto);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @GetMapping("/v1/{id}")
    public ResponseEntity<CustomResponseDto<List<TaskCommentResponseDto>>> getAllTaskComments(@PathVariable UUID id) {
        CustomResponseDto<List<TaskCommentResponseDto>> response = taskCommentService.getTaskCommentsByTaskId(id);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @DeleteMapping("/v1/{id}")
    public ResponseEntity<CustomResponseDto> deleteTaskComment(@PathVariable UUID id) {
        CustomResponseDto response = taskCommentService.deleteTaskComment(id);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }
}
