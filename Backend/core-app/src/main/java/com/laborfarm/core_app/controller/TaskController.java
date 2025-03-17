package com.laborfarm.core_app.controller;

import com.laborfarm.core_app.service.TaskService;
import com.laborfarm.core_app.service.dto.CustomResponseDto;
import com.laborfarm.core_app.service.dto.TaskRequestDto;
import com.laborfarm.core_app.service.dto.TaskResponseDto;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/v1")
    public ResponseEntity<CustomResponseDto<TaskResponseDto>> createTask(@RequestBody TaskRequestDto taskRequestDto) {
        CustomResponseDto<TaskResponseDto> response = taskService.addTask(taskRequestDto);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @PutMapping("/v1")
    public ResponseEntity<CustomResponseDto<TaskResponseDto>> updateTask(@RequestBody TaskRequestDto taskRequestDto) {
        CustomResponseDto<TaskResponseDto> response = taskService.updateTask(taskRequestDto);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @DeleteMapping("/v1/{id}")
    public ResponseEntity<CustomResponseDto> deleteTask(@PathVariable UUID id) {
        CustomResponseDto response = taskService.deleteTask(id);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @GetMapping("/v1/{id}")
    public ResponseEntity<CustomResponseDto<TaskResponseDto>> getTaskById(@PathVariable UUID id) {
        CustomResponseDto<TaskResponseDto> response = taskService.getTaskById(id);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @GetMapping("/v1")
    public ResponseEntity<CustomResponseDto<List<TaskResponseDto>>> getAllTasks(){
        CustomResponseDto<List<TaskResponseDto>> response = taskService.getAllTasks();
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @GetMapping("/v1/state/{id}")
    public ResponseEntity<CustomResponseDto<List<TaskResponseDto>>> getTasksByState(@PathVariable int id){
        CustomResponseDto<List<TaskResponseDto>> response = taskService.getAllTasksByStateId(id);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @GetMapping("/v1/priority/{id}")
    public ResponseEntity<CustomResponseDto<List<TaskResponseDto>>> getTasksByPriority(@PathVariable int id){
        CustomResponseDto<List<TaskResponseDto>> response = taskService.getAllTasksByPriorityId(id);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @GetMapping("/v1/project/{id}")
    public ResponseEntity<CustomResponseDto<List<TaskResponseDto>>> getTasksByProject(@PathVariable UUID id){
        CustomResponseDto<List<TaskResponseDto>> response = taskService.getProjectTasks(id);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @GetMapping("/v1/user/{id}")
    public ResponseEntity<CustomResponseDto<List<TaskResponseDto>>> getTasksByUser(@PathVariable UUID id){
        CustomResponseDto<List<TaskResponseDto>> response = taskService.getUserTasks(id);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }
}
