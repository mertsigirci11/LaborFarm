package com.laborfarm.core_app;

import com.laborfarm.core_app.entity.task.State;
import com.laborfarm.core_app.entity.task.Task;
import com.laborfarm.core_app.repository.TaskRepository;
import com.laborfarm.core_app.service.dto.CustomResponseDto;
import com.laborfarm.core_app.service.dto.TaskRequestDto;
import com.laborfarm.core_app.service.dto.TaskResponseDto;
import com.laborfarm.core_app.service.exception.project.ProjectNotFoundException;
import com.laborfarm.core_app.service.exception.task.*;
import com.laborfarm.core_app.service.exception.user.UserNotFoundException;
import com.laborfarm.core_app.service.implementation.TaskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    private ModelMapper modelMapper = new ModelMapper();


    @BeforeEach
    void setUp() {
        modelMapper.addMappings(new PropertyMap<Task, TaskResponseDto>() {
            @Override
            protected void configure() {
                map().setId(source.getId());
                map().setStateId(source.getStateId());
                map().setPriorityId(source.getPriorityId());
                map().setProjectId(source.getProjectId());
                map().setAssigneeId(source.getAssigneeId());
            }
        });
        taskService = new TaskServiceImpl(taskRepository, modelMapper);
    }

    @Test
    public void addTask_ReturnsNewTask() {
        //Arrange
        TaskRequestDto taskRequestDto = new TaskRequestDto();
        taskRequestDto.setTitle("Title");
        taskRequestDto.setStateId(1);
        taskRequestDto.setPriorityId(1);
        taskRequestDto.setProjectId(UUID.randomUUID());
        taskRequestDto.setAssigneeId(UUID.randomUUID());

        Task savedTask = new Task();
        savedTask.setTitle("Title");
        savedTask.setStateId(1);
        savedTask.setPriorityId(1);
        savedTask.setId(UUID.randomUUID());

        Task task = modelMapper.map(taskRequestDto, Task.class);

        TaskResponseDto responseDto = modelMapper.map(savedTask, TaskResponseDto.class);

        when(taskRepository.isProjectExist(any(UUID.class))).thenReturn(true);
        when(taskRepository.isAssigneeExist(any(UUID.class))).thenReturn(true);
        when(taskRepository.save(task)).thenReturn(savedTask);

        //Act
        CustomResponseDto<TaskResponseDto> response = taskService.addTask(taskRequestDto);

        //Assert
        assertThat(response.getData().getId()).isEqualTo(responseDto.getId());
    }

    @Test
    public void addTask_WhenProjectIsNotExists_ThrowsProjectNotFoundException() {
        //Arrange
        TaskRequestDto taskRequestDto = new TaskRequestDto();
        taskRequestDto.setTitle("Title");
        taskRequestDto.setStateId(1);
        taskRequestDto.setPriorityId(1);
        taskRequestDto.setProjectId(UUID.randomUUID());
        taskRequestDto.setAssigneeId(UUID.randomUUID());

        when(taskRepository.isProjectExist(any(UUID.class))).thenReturn(false);

        //Act
        //Assert
        assertThrows(ProjectNotFoundException.class, () -> taskService.addTask(taskRequestDto));
    }

    @Test
    public void addTask_WhenAssigneeIsNotExists_ThrowsUserNotFoundException() {
        //Arrange
        TaskRequestDto taskRequestDto = new TaskRequestDto();
        taskRequestDto.setTitle("Title");
        taskRequestDto.setStateId(1);
        taskRequestDto.setPriorityId(1);
        taskRequestDto.setProjectId(UUID.randomUUID());
        taskRequestDto.setAssigneeId(UUID.randomUUID());

        when(taskRepository.isProjectExist(any(UUID.class))).thenReturn(true);
        when(taskRepository.isAssigneeExist(any(UUID.class))).thenReturn(false);

        //Act
        //Assert
        assertThrows(UserNotFoundException.class, () -> taskService.addTask(taskRequestDto));
    }

    @Test
    public void addTask_WhenStateIsNotExists_ThrowsStateNotFoundException() {
        //Arrange
        TaskRequestDto taskRequestDto = new TaskRequestDto();
        taskRequestDto.setTitle("Title");
        taskRequestDto.setStateId(99); //Options -> 1 to 6
        taskRequestDto.setPriorityId(1);
        taskRequestDto.setProjectId(UUID.randomUUID());
        taskRequestDto.setAssigneeId(UUID.randomUUID());

        when(taskRepository.isProjectExist(any(UUID.class))).thenReturn(true);
        when(taskRepository.isAssigneeExist(any(UUID.class))).thenReturn(true);

        //Act
        //Assert
        assertThrows(StateNotFoundException.class, () -> taskService.addTask(taskRequestDto));
    }

    @Test
    public void addTask_WhenPriorityIsNotExists_ThrowsPriorityNotFoundException() {
        //Arrange
        TaskRequestDto taskRequestDto = new TaskRequestDto();
        taskRequestDto.setTitle("Title");
        taskRequestDto.setStateId(1);
        taskRequestDto.setPriorityId(4); //Options -> 1 to 3
        taskRequestDto.setProjectId(UUID.randomUUID());
        taskRequestDto.setAssigneeId(UUID.randomUUID());

        when(taskRepository.isProjectExist(any(UUID.class))).thenReturn(true);
        when(taskRepository.isAssigneeExist(any(UUID.class))).thenReturn(true);

        //Act
        //Assert
        assertThrows(PriorityNotFoundException.class, () -> taskService.addTask(taskRequestDto));
    }

    @Test
    public void updateTask_ReturnsUpdatedTask() {
        //Arrange
        UUID taskId = UUID.randomUUID();
        TaskRequestDto taskRequestDto = new TaskRequestDto();
        taskRequestDto.setId(taskId);
        taskRequestDto.setStateId(2);
        taskRequestDto.setPriorityId(1);
        taskRequestDto.setProjectId(UUID.randomUUID());
        taskRequestDto.setAssigneeId(UUID.randomUUID());

        Task existingTask = new Task();
        existingTask.setId(taskId);
        existingTask.setStateId(1);
        existingTask.setPriorityId(2);


        Task updatedTask = modelMapper.map(taskRequestDto, Task.class);

        when(taskRepository.isProjectExist(any(UUID.class))).thenReturn(true);
        when(taskRepository.isAssigneeExist(any(UUID.class))).thenReturn(true);
        when(taskRepository.findByIdAndIsActiveTrue(taskRequestDto.getId())).thenReturn(existingTask);
        when(taskRepository.save(existingTask)).thenReturn(updatedTask);

        //Act
        CustomResponseDto<TaskResponseDto> response = taskService.updateTask(taskRequestDto);

        //Assert
        assertThat(response.getData().getStateId()).isEqualTo(updatedTask.getStateId());
        assertThat(response.getData().getPriorityId()).isEqualTo(updatedTask.getPriorityId());
    }

    @Test
    public void updateTask_WhenTaskIsNotExists_ThrowsTaskNotFoundException() {
        //Arrange
        TaskRequestDto taskRequestDto = new TaskRequestDto();
        taskRequestDto.setId(UUID.randomUUID());
        taskRequestDto.setStateId(1);
        taskRequestDto.setPriorityId(1);
        taskRequestDto.setProjectId(UUID.randomUUID());
        taskRequestDto.setAssigneeId(UUID.randomUUID());
        when(taskRepository.isProjectExist(any(UUID.class))).thenReturn(true);
        when(taskRepository.isAssigneeExist(any(UUID.class))).thenReturn(true);
        when(taskRepository.findByIdAndIsActiveTrue(taskRequestDto.getId())).thenReturn(null);
        //Act
        //Assert
        assertThrows(TaskNotFoundException.class, () -> taskService.updateTask(taskRequestDto));
    }

    @Test
    public void updateTask_WhenTaskStateIsCompleted_ThrowsCompletedTaskException() {
        // Arrange
        TaskRequestDto taskRequestDto = new TaskRequestDto();
        taskRequestDto.setId(UUID.randomUUID());
        taskRequestDto.setStateId(2);
        taskRequestDto.setPriorityId(1);
        taskRequestDto.setProjectId(UUID.randomUUID());
        taskRequestDto.setAssigneeId(UUID.randomUUID());

        Task existingTask = new Task();
        existingTask.setStateId(State.COMPLETED.getValue());

        when(taskRepository.isProjectExist(any(UUID.class))).thenReturn(true);
        when(taskRepository.isAssigneeExist(any(UUID.class))).thenReturn(true);
        when(taskRepository.findByIdAndIsActiveTrue(taskRequestDto.getId())).thenReturn(existingTask);

        // Act & Assert
        assertThrows(CompletedTaskException.class, () -> taskService.updateTask(taskRequestDto));
    }

    @Test
    public void updateTask_WhenRequestStateIsBlockedAndTaskStateIsBacklog_ThrowsStateCantSetAsBlockedException(){
        //Arrange
        TaskRequestDto taskRequestDto = new TaskRequestDto();
        taskRequestDto.setId(UUID.randomUUID());
        taskRequestDto.setStateId(State.BLOCKED.getValue());
        taskRequestDto.setPriorityId(1);
        taskRequestDto.setProjectId(UUID.randomUUID());
        taskRequestDto.setAssigneeId(UUID.randomUUID());

        Task existingTask = new Task();
        existingTask.setStateId(State.BACKLOG.getValue());

        when(taskRepository.isProjectExist(any(UUID.class))).thenReturn(true);
        when(taskRepository.isAssigneeExist(any(UUID.class))).thenReturn(true);
        when(taskRepository.findByIdAndIsActiveTrue(taskRequestDto.getId())).thenReturn(existingTask);
        //Act
        //Assert
        assertThrows(StateCantSetAsBlockedException.class, () -> taskService.updateTask(taskRequestDto));
    }

    @Test
    public void updateTask_WhenRequestStateIsCancelledAndReasonIsEmpty_ThrowsCancelledOrBlockedReasonException(){
        //Arrange
        TaskRequestDto taskRequestDto = new TaskRequestDto();
        taskRequestDto.setId(UUID.randomUUID());
        taskRequestDto.setStateId(State.CANCELLED.getValue());
        taskRequestDto.setPriorityId(1);
        taskRequestDto.setProjectId(UUID.randomUUID());
        taskRequestDto.setAssigneeId(UUID.randomUUID());
        taskRequestDto.setCancelledOrBlockedReason("");

        Task existingTask = new Task();

        when(taskRepository.isProjectExist(any(UUID.class))).thenReturn(true);
        when(taskRepository.isAssigneeExist(any(UUID.class))).thenReturn(true);
        when(taskRepository.findByIdAndIsActiveTrue(taskRequestDto.getId())).thenReturn(existingTask);
        //Act
        //Assert
        assertThrows(CancelledOrBlockedReasonException.class, () -> taskService.updateTask(taskRequestDto));
    }

    @Test
    public void deleteTask_ReturnsNoContent() {
        //Arrange
        when(taskRepository.findByIdAndIsActiveTrue(any(UUID.class))).thenReturn(new Task());
        //Act
        CustomResponseDto response = taskService.deleteTask(UUID.randomUUID());
        //Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
    @Test
    public void deleteTask_WhenTaskIsNotExists_ThrowsTaskNotFoundException() {
        //Arrange
        when(taskRepository.findByIdAndIsActiveTrue(any(UUID.class))).thenReturn(null);
        //Act
        //Assert
        assertThrows(TaskNotFoundException.class, () -> taskService.deleteTask(UUID.randomUUID()));
    }

    @Test
    public void getTaskById_ReturnsTask() {
        //Arrange
        UUID taskId = UUID.randomUUID();
        Task existingTask = new Task();
        existingTask.setId(taskId);
        TaskResponseDto responseDto = modelMapper.map(existingTask, TaskResponseDto.class);

        when(taskRepository.findByIdAndIsActiveTrue(taskId)).thenReturn(existingTask);
        //Act
        CustomResponseDto<TaskResponseDto> response = taskService.getTaskById(taskId);
        //Assert
        assertThat(response.getData()).isEqualTo(responseDto);
    }

    @Test
    public void getTaskById_WhenTaskIsNotExists_ThrowsTaskNotFoundException() {
        //Arrange
        when(taskRepository.findByIdAndIsActiveTrue(any(UUID.class))).thenReturn(null);
        //Act
        //Assert
        assertThrows(TaskNotFoundException.class, () -> taskService.getTaskById(UUID.randomUUID()));
    }
    
    @Test
    public void getAllTasks_ReturnsAllTasks() {
        //Arrange
        Task task1 = new Task();
        task1.setId(UUID.randomUUID());
        List<Task> taskList = List.of(task1);

        List<TaskResponseDto> taskResponseDtoList = List.of(modelMapper.map(task1, TaskResponseDto.class));
        when(taskRepository.findByIsActiveTrue()).thenReturn(taskList);

        //Act
        CustomResponseDto<List<TaskResponseDto>> response = taskService.getAllTasks();
        //Assert
        assertThat(response.getData()).isEqualTo(taskResponseDtoList);
    }

    @Test
    public void getAllTasksByStateId_ReturnsAllTasksByStateId() {
        //Arrange
        Task task1 = new Task();
        task1.setStateId(1);
        List<Task> taskList = List.of(task1);
        List<TaskResponseDto> taskDtoList = List.of(modelMapper.map(task1, TaskResponseDto.class));

        when(taskRepository.getTasksByStateId(1)).thenReturn(taskList);
        //Act
        CustomResponseDto<List<TaskResponseDto>> response = taskService.getAllTasksByStateId(1);
        //Assert
        assertThat(response.getData()).isEqualTo(taskDtoList);
    }

    @Test
    public void getAllTasksByPriorityId_ReturnsAllTasksByPriorityId() {
        //Arrange
        Task task1 = new Task();
        task1.setPriorityId(1);
        List<Task> taskList = List.of(task1);
        List<TaskResponseDto> taskDtoList = List.of(modelMapper.map(task1, TaskResponseDto.class));

        when(taskRepository.getTasksByPriorityId(1)).thenReturn(taskList);
        //Act
        CustomResponseDto<List<TaskResponseDto>> response = taskService.getAllTasksByPriorityId(1);
        //Assert
        assertThat(response.getData().get(0)).isEqualTo(taskDtoList.get(0));
    }

    @Test
    public void getProjectTasks_ReturnsProjectTasks() {
        //Arrange
        UUID projectId = UUID.randomUUID();
        Task task1 = new Task();
        task1.setProjectId(projectId);
        List<Task> taskList = List.of(task1);
        List<TaskResponseDto> taskDtoList = List.of(modelMapper.map(task1, TaskResponseDto.class));

        when(taskRepository.getTasksByProjectId(projectId)).thenReturn(taskList);
        //Act
        CustomResponseDto<List<TaskResponseDto>> response = taskService.getProjectTasks(projectId);
        //Assert
        assertThat(response.getData().get(0)).isEqualTo(taskDtoList.get(0));
    }

    @Test
    public void getUserTasks_ReturnsUserTasks() {
        //Arrange
        UUID userId = UUID.randomUUID();
        Task task1 = new Task();
        task1.setAssigneeId(userId);
        List<Task> taskList = List.of(task1);
        List<TaskResponseDto> taskDtoList = List.of(modelMapper.map(task1, TaskResponseDto.class));

        when(taskRepository.getTasksByUserId(userId)).thenReturn(taskList);
        //Act
        CustomResponseDto<List<TaskResponseDto>> response = taskService.getUserTasks(userId);
        //Assert
        assertThat(response.getData().get(0)).isEqualTo(taskDtoList.get(0));
    }
}
