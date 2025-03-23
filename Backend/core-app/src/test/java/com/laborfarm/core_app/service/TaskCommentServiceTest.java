package com.laborfarm.core_app.service;

import com.laborfarm.core_app.entity.task.TaskComment;
import com.laborfarm.core_app.repository.TaskCommentRepository;
import com.laborfarm.core_app.service.dto.CustomResponseDto;
import com.laborfarm.core_app.service.dto.TaskCommentRequestDto;
import com.laborfarm.core_app.service.dto.TaskCommentResponseDto;
import com.laborfarm.core_app.service.exception.task.TaskCommentNotFoundException;
import com.laborfarm.core_app.service.implementation.TaskCommentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskCommentServiceTest {

    @Mock
    private TaskCommentRepository taskCommentRepository;

    @InjectMocks
    private TaskCommentServiceImpl taskCommentService;

    private ModelMapper modelMapper = new ModelMapper();

    @BeforeEach
    void setUp() {
        modelMapper.addMappings(new PropertyMap<TaskComment, TaskCommentResponseDto>(){
            @Override
            protected void configure() {
                map().setId(source.getId());
                map().setCreatorUserId(source.getCreatorUserId());
                map().setTaskId(source.getTaskId());
            }
        });

        taskCommentService = new TaskCommentServiceImpl(taskCommentRepository, modelMapper);
    }

    @Test
    public void addTaskComment_ReturnsNewTaskComment() {
        //Arrange
        UUID taskId = UUID.randomUUID();
        UUID creatorUserId = UUID.randomUUID();
        TaskCommentRequestDto taskCommentRequestDto = new TaskCommentRequestDto();
        taskCommentRequestDto.setTaskId(taskId);
        taskCommentRequestDto.setCreatorUserId(creatorUserId);
        taskCommentRequestDto.setComment("Hi");

        TaskComment taskCommentToBeAdded = modelMapper.map(taskCommentRequestDto, TaskComment.class);
        when(taskCommentRepository.save(taskCommentToBeAdded)).thenReturn(taskCommentToBeAdded);
        TaskCommentResponseDto responseDto = modelMapper.map(taskCommentToBeAdded, TaskCommentResponseDto.class);

        //Act
        CustomResponseDto<TaskCommentResponseDto> response = taskCommentService.addTaskComment(taskCommentRequestDto);
        //Assert
        assertThat(response.getData().getId()).isEqualTo(responseDto.getId());
    }

    @Test
    public void updateTaskComment_returnsUpdatedTaskComment() {
        //Arrange
        UUID taskId = UUID.randomUUID();
        UUID commentId = UUID.randomUUID();
        TaskCommentRequestDto taskCommentRequestDto = new TaskCommentRequestDto();
        taskCommentRequestDto.setTaskId(taskId);
        taskCommentRequestDto.setId(commentId);
        taskCommentRequestDto.setComment("Hi");

        TaskComment existingTaskComment = new TaskComment();
        existingTaskComment.setId(taskId);
        existingTaskComment.setComment("Hello");
        when(taskCommentRepository.findByIdAndIsActiveTrue(commentId)).thenReturn(existingTaskComment);
        when(taskCommentRepository.save(existingTaskComment)).thenReturn(existingTaskComment);
        //Act
        CustomResponseDto<TaskCommentResponseDto> response = taskCommentService.updateTaskComment(taskCommentRequestDto);
        //Assert
        assertThat(response.getData().getComment()).isEqualTo(taskCommentRequestDto.getComment());
    }

    @Test
    public void updateTaskComment_WhenTaskCommentIsNotFound_ThrowsTaskCommentNotFoundException(){
        //Arrange
        TaskCommentRequestDto taskCommentRequestDto = new TaskCommentRequestDto();
        taskCommentRequestDto.setId(UUID.randomUUID());
        when(taskCommentRepository.findByIdAndIsActiveTrue(any(UUID.class))).thenReturn(null);
        //Act
        //Assert
        assertThrows(TaskCommentNotFoundException.class, ()-> taskCommentService.updateTaskComment(taskCommentRequestDto));
    }

    @Test
    public void getTaskCommentsByTaskId_ReturnTaskComments() {
        //Arrange
        UUID taskId = UUID.randomUUID();
        TaskComment taskComment = new TaskComment();
        taskComment.setTaskId(taskId);
        List<TaskComment> taskComments = List.of(taskComment);
        List<TaskCommentResponseDto> taskCommentResponseDtos = List.of(modelMapper.map(taskComment, TaskCommentResponseDto.class));
        when((taskCommentRepository.findByTaskIdAndIsActiveTrue(taskId))).thenReturn(taskComments);
        //Act
        CustomResponseDto<List<TaskCommentResponseDto>> response = taskCommentService.getTaskCommentsByTaskId(taskId);
        //Assert
        assertThat(response.getData().getFirst()).isEqualTo(taskCommentResponseDtos.getFirst());
    }

    @Test
    public void deleteTaskComment_ReturnsNoContent() {
        //Arrange
        UUID taskCommentId = UUID.randomUUID();
        TaskComment taskComment = new TaskComment();
        taskComment.setActive(true);
        taskComment.setId(taskCommentId);
        when(taskCommentRepository.findByIdAndIsActiveTrue(taskCommentId)).thenReturn(taskComment);
        //Act
        CustomResponseDto response = taskCommentService.deleteTaskComment(taskCommentId);
        //Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
        assertThat(taskComment.isActive()).isFalse();
    }

    @Test
    public void deleteTaskComment_WhenTaskCommentIsNotFound_ThrowsTaskCommentNotFoundException(){
        //Arrange
        UUID taskCommentId = UUID.randomUUID();
        when(taskCommentRepository.findByIdAndIsActiveTrue(taskCommentId)).thenReturn(null);
        //Act
        //Assert
        assertThrows(TaskCommentNotFoundException.class, ()-> taskCommentService.deleteTaskComment(taskCommentId));
    }
}
