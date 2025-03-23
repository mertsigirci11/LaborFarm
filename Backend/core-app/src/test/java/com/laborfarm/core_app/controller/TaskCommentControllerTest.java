package com.laborfarm.core_app.controller;

import com.laborfarm.core_app.service.TaskCommentService;
import com.laborfarm.core_app.service.dto.CustomResponseDto;
import com.laborfarm.core_app.service.dto.TaskCommentRequestDto;
import com.laborfarm.core_app.service.dto.TaskCommentResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskCommentControllerTest {

    @InjectMocks
    private TaskCommentController taskCommentController;

    @Mock
    private TaskCommentService taskCommentService;

    private UUID sampleTaskId;
    private UUID sampleUserId;
    private UUID sampleCommentId;
    private TaskCommentRequestDto requestDto;
    private TaskCommentResponseDto responseDto;
    private List<TaskCommentResponseDto> responseList;

    @BeforeEach
    void setUp() {
        sampleTaskId = UUID.randomUUID();
        sampleUserId = UUID.randomUUID();
        sampleCommentId = UUID.randomUUID();

        requestDto = new TaskCommentRequestDto();
        requestDto.setTaskId(sampleTaskId);
        requestDto.setCreatorUserId(sampleUserId);
        requestDto.setComment("Sample Comment");

        responseDto = new TaskCommentResponseDto();
        responseDto.setId(sampleCommentId);
        responseDto.setTaskId(sampleTaskId);
        responseDto.setCreatorUserId(sampleUserId);
        responseDto.setComment("Sample Comment");

        responseList = new ArrayList<>();
        responseList.add(responseDto);
    }

    @Test
    void testAddTaskComment() {
        CustomResponseDto<TaskCommentResponseDto> mockResponse = CustomResponseDto.success(201, responseDto);
        when(taskCommentService.addTaskComment(requestDto)).thenReturn(mockResponse);

        ResponseEntity<CustomResponseDto<TaskCommentResponseDto>> response = taskCommentController.addTaskComment(requestDto);

        assertEquals(201, response.getBody().getStatusCode());
        assertEquals(sampleCommentId, response.getBody().getData().getId());
    }

    @Test
    void testUpdateTaskComment() {
        CustomResponseDto<TaskCommentResponseDto> mockResponse = CustomResponseDto.success(200, responseDto);
        when(taskCommentService.updateTaskComment(requestDto)).thenReturn(mockResponse);

        ResponseEntity<CustomResponseDto<TaskCommentResponseDto>> response = taskCommentController.updateTaskComment(requestDto);

        assertEquals(200, response.getBody().getStatusCode());
        assertEquals(sampleCommentId, response.getBody().getData().getId());
    }

    @Test
    void testGetAllTaskComments() {
        CustomResponseDto<List<TaskCommentResponseDto>> mockResponse = CustomResponseDto.success(200, responseList);
        when(taskCommentService.getTaskCommentsByTaskId(sampleTaskId)).thenReturn(mockResponse);

        ResponseEntity<CustomResponseDto<List<TaskCommentResponseDto>>> response = taskCommentController.getAllTaskComments(sampleTaskId);

        assertEquals(200, response.getBody().getStatusCode());
        assertEquals(1, response.getBody().getData().size());
    }

    @Test
    void testDeleteTaskComment() {
        CustomResponseDto<Void> mockResponse = CustomResponseDto.success(204);
        when(taskCommentService.deleteTaskComment(sampleCommentId)).thenReturn(mockResponse);

        ResponseEntity<CustomResponseDto> response = taskCommentController.deleteTaskComment(sampleCommentId);

        assertEquals(204, response.getBody().getStatusCode());
    }
}