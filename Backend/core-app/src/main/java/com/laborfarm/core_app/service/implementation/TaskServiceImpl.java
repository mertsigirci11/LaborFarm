package com.laborfarm.core_app.service.implementation;

import com.laborfarm.core_app.entity.task.Priority;
import com.laborfarm.core_app.entity.task.State;
import com.laborfarm.core_app.entity.task.Task;
import com.laborfarm.core_app.repository.TaskRepository;
import com.laborfarm.core_app.service.TaskService;
import com.laborfarm.core_app.service.dto.*;
import com.laborfarm.core_app.service.exception.project.ProjectNotFoundException;
import com.laborfarm.core_app.service.exception.task.PriorityNotFoundException;
import com.laborfarm.core_app.service.exception.task.StateNofFoundException;
import com.laborfarm.core_app.service.exception.task.TaskNotFoundException;
import com.laborfarm.core_app.service.exception.user.UserNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, ModelMapper modelMapper) {
        this.taskRepository = taskRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CustomResponseDto<TaskResponseDto> addTask(TaskRequestDto taskRequestDto) {
        checkFKExistence(taskRequestDto);

        Task task = modelMapper.map(taskRequestDto, Task.class);
        task.setCreatedAt(new Date());
        task.setActive(true);
        task.setPriorityId(taskRequestDto.getPriorityId());
        task.setStateId(taskRequestDto.getStateId());

        Task savedTask = taskRepository.save(task);
        TaskResponseDto response = modelMapper.map(savedTask, TaskResponseDto.class);

        return CustomResponseDto.success(HttpStatus.CREATED.value(), response);
    }

    @Override
    public CustomResponseDto<TaskResponseDto> updateTask(TaskRequestDto taskRequestDto) {
        checkFKExistence(taskRequestDto);
        checkState(taskRequestDto);

        Task task = taskRepository.findByIdAndIsActiveTrue(taskRequestDto.getId());
        if (task == null) {
            throw new TaskNotFoundException();
        }

        task = modelMapper.map(taskRequestDto, Task.class);
        task.setUpdatedAt(new Date());
        task.setActive(true);

        Task savedTask = taskRepository.save(task);
        TaskResponseDto response = modelMapper.map(savedTask, TaskResponseDto.class);

        return CustomResponseDto.success(HttpStatus.OK.value(), response);
    }

    @Override
    public CustomResponseDto deleteTask(UUID taskId) {
        Task task = taskRepository.findByIdAndIsActiveTrue(taskId);
        if (task == null) {
            throw new TaskNotFoundException();
        }
        task.setActive(false);
        task.setUpdatedAt(new Date());
        taskRepository.save(task);

        return CustomResponseDto.success(HttpStatus.NO_CONTENT.value());
    }

    @Override
    public CustomResponseDto<TaskResponseDto> getTaskById(UUID taskId) {
        Task task = taskRepository.findByIdAndIsActiveTrue(taskId);
        if (task == null) {
            throw new TaskNotFoundException();
        }

        TaskResponseDto response = modelMapper.map(task, TaskResponseDto.class);

        return CustomResponseDto.success(HttpStatus.OK.value(), response);
    }

    @Override
    public CustomResponseDto<List<TaskResponseDto>> getAllTasks() {
        List<TaskResponseDto> tasks = taskRepository.findByIsActiveTrue()
                .stream()
                .map(this::convertToDto)
                .toList();

        return CustomResponseDto.success(HttpStatus.OK.value(), tasks);
    }

    @Override
    public CustomResponseDto<List<TaskResponseDto>> getAllTasksByStateId(int stateId) {
        return null;
    }

    @Override
    public CustomResponseDto<List<TaskResponseDto>> getAllTasksByPriorityId(int priorityId) {
        return null;
    }

    @Override
    public CustomResponseDto<List<TaskResponseDto>> getProjectTasks(UUID projectId) {
        return null;
    }

    @Override
    public CustomResponseDto<List<TaskResponseDto>> getUserTasks(UUID userId) {
        return null;
    }

    //Helper methods
    private TaskResponseDto convertToDto(Task task) {
        return modelMapper.map(task, TaskResponseDto.class);
    }

    private Task convertToEntity(TaskRequestDto taskRequestDto) {
        return modelMapper.map(taskRequestDto, Task.class);
    }

    private void checkFKExistence(TaskRequestDto taskRequestDto){
        //Check if project exists
        boolean isProjectExists = taskRepository.isProjectExist(taskRequestDto.getProjectId());
        if (!isProjectExists) {
            throw new ProjectNotFoundException();
        }

        //Check if assignee exists
        boolean isAssigneeExists = taskRepository.isAssigneeExist(taskRequestDto.getAssigneeId());
        if (!isAssigneeExists) {
            throw new UserNotFoundException();
        }

        //Check if state exists
        State state = State.fromValue(taskRequestDto.getStateId());
        if (state == null) {
            throw new StateNofFoundException();
        }

        //Check if priority exists
        Priority priority = Priority.fromValue(taskRequestDto.getPriorityId());
        if (priority == null) {
            throw new PriorityNotFoundException();
        }
    }

    private void checkState(TaskRequestDto taskRequestDto) {
    }
}