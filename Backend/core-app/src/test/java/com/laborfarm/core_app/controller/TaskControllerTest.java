package com.laborfarm.core_app.controller;

import com.laborfarm.core_app.service.TaskService;
import com.laborfarm.core_app.service.dto.CustomResponseDto;
import com.laborfarm.core_app.service.dto.TaskRequestDto;
import com.laborfarm.core_app.service.dto.TaskResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(MockitoExtension.class)
class TaskControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    private UUID taskId;
    private TaskResponseDto taskResponseDto;
    private CustomResponseDto<TaskResponseDto> responseDto;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();

        taskId = UUID.randomUUID();
        taskResponseDto = new TaskResponseDto();
        taskResponseDto.setId(taskId);
        taskResponseDto.setTitle("Test Task");

        responseDto = CustomResponseDto.success(200, taskResponseDto);
    }

    @Test
    void getAllTasks_ReturnsTaskList() throws Exception {
        List<TaskResponseDto> taskList = List.of(taskResponseDto);
        CustomResponseDto<List<TaskResponseDto>> listResponse = CustomResponseDto.success(200, taskList);

        when(taskService.getAllTasks()).thenReturn(listResponse);

        mockMvc.perform(get("/api/tasks/v1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].title").value("Test Task"));
    }

    @Test
    void getTaskById_ReturnsTask() throws Exception {
        when(taskService.getTaskById(taskId)).thenReturn(responseDto);

        mockMvc.perform(get("/api/tasks/v1/{id}", taskId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.title").value("Test Task"));
    }

    @Test
    void createTask_ReturnsCreatedTask() throws Exception {
        TaskRequestDto requestDto = new TaskRequestDto();
        requestDto.setTitle("New Task");

        when(taskService.addTask(any())).thenReturn(responseDto);

        mockMvc.perform(post("/api/tasks/v1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"New Task\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.title").value("Test Task"));
    }

    @Test
    void updateTask_ReturnsUpdatedTask() throws Exception {
        TaskRequestDto requestDto = new TaskRequestDto();
        requestDto.setId(taskId);
        requestDto.setTitle("Updated Task");

        when(taskService.updateTask(any())).thenReturn(responseDto);

        mockMvc.perform(put("/api/tasks/v1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": \"" + taskId + "\", \"title\": \"Updated Task\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.title").value("Test Task"));
    }

    @Test
    void deleteTask_ReturnsSuccessResponse() throws Exception {
        CustomResponseDto<Void> deleteResponse = CustomResponseDto.success(200, null);
        when(taskService.deleteTask(taskId)).thenReturn(deleteResponse);

        mockMvc.perform(delete("/api/tasks/v1/{id}", taskId))
                .andExpect(status().isOk());
    }

    @Test
    void getTasksByState_ReturnsTaskList() throws Exception {
        List<TaskResponseDto> taskList = List.of(taskResponseDto);
        CustomResponseDto<List<TaskResponseDto>> response = CustomResponseDto.success(200, taskList);

        when(taskService.getAllTasksByStateId(1)).thenReturn(response);

        mockMvc.perform(get("/api/tasks/v1/state/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].title").value("Test Task"));
    }

    @Test
    void getTasksByPriority_ReturnsTaskList() throws Exception {
        List<TaskResponseDto> taskList = List.of(taskResponseDto);
        CustomResponseDto<List<TaskResponseDto>> response = CustomResponseDto.success(200, taskList);

        when(taskService.getAllTasksByPriorityId(1)).thenReturn(response);

        mockMvc.perform(get("/api/tasks/v1/priority/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].title").value("Test Task"));
    }

    @Test
    void getTasksByProject_ReturnsTaskList() throws Exception {
        List<TaskResponseDto> taskList = List.of(taskResponseDto);
        CustomResponseDto<List<TaskResponseDto>> response = CustomResponseDto.success(200, taskList);

        when(taskService.getProjectTasks(taskId)).thenReturn(response);

        mockMvc.perform(get("/api/tasks/v1/project/{id}", taskId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].title").value("Test Task"));
    }

    @Test
    void getTasksByUser_ReturnsTaskList() throws Exception {
        List<TaskResponseDto> taskList = List.of(taskResponseDto);
        CustomResponseDto<List<TaskResponseDto>> response = CustomResponseDto.success(200, taskList);

        when(taskService.getUserTasks(taskId)).thenReturn(response);

        mockMvc.perform(get("/api/tasks/v1/user/{id}", taskId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].title").value("Test Task"));
    }
}
