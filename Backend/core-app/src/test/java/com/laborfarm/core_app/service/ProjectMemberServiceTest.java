package com.laborfarm.core_app.service;

import com.laborfarm.core_app.entity.project.ProjectMember;
import com.laborfarm.core_app.repository.ProjectMemberRepository;
import com.laborfarm.core_app.service.dto.CustomResponseDto;
import com.laborfarm.core_app.service.dto.ProjectMemberRequestDto;
import com.laborfarm.core_app.service.dto.ProjectMemberResponseDto;
import com.laborfarm.core_app.service.exception.project.ProjectMemberNotFound;
import com.laborfarm.core_app.service.exception.project.ProjectNotFoundException;
import com.laborfarm.core_app.service.exception.project.UserAlreadyExistsInProject;
import com.laborfarm.core_app.service.exception.user.UserNotFoundException;
import com.laborfarm.core_app.service.implementation.ProjectMemberServiceImpl;
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
public class ProjectMemberServiceTest {

    @Mock
    private ProjectMemberRepository projectMemberRepository;

    @InjectMocks
    private ProjectMemberServiceImpl projectMemberService;

    private ModelMapper modelMapper = new ModelMapper();

    @BeforeEach
    void setUp() {
        modelMapper.addMappings(new PropertyMap<ProjectMember, ProjectMemberResponseDto>() {
            @Override
            protected void configure() {
                map().setUserId(source.getUser().getId());
                map().setProjectId(source.getProject().getId());
            }
        });
        projectMemberService = new ProjectMemberServiceImpl(projectMemberRepository, modelMapper);
    }

    @Test
    public void addProjectMember_ReturnsCreated() {
        //Arrange
        UUID userId = UUID.randomUUID();
        UUID projectId = UUID.randomUUID();
        ProjectMemberRequestDto projectMemberRequestDto = new ProjectMemberRequestDto();
        projectMemberRequestDto.setUserId(userId);
        projectMemberRequestDto.setProjectId(projectId);
        when(projectMemberRepository.isUserExists(projectMemberRequestDto.getUserId())).thenReturn(true);
        when(projectMemberRepository.isProjectExists(projectMemberRequestDto.getProjectId())).thenReturn(true);
        when(projectMemberRepository.isUserExistsInProject(projectId, userId)).thenReturn(false);
        ProjectMember projectMember = modelMapper.map(projectMemberRequestDto, ProjectMember.class);
        when((projectMemberRepository.save(projectMember))).thenReturn(projectMember);
        //Act
        CustomResponseDto<ProjectMemberResponseDto> response = projectMemberService.addProjectMember(projectMemberRequestDto);
        //Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    public void addProjectMember_WhenUserNotExists_ThrowUserNotFoundException() {
        //Arrange
        ProjectMemberRequestDto projectMemberRequestDto = new ProjectMemberRequestDto();
        projectMemberRequestDto.setUserId(UUID.randomUUID());
        when(projectMemberRepository.isUserExists(projectMemberRequestDto.getUserId())).thenReturn(false);
        //Act
        //Assert
        assertThrows(UserNotFoundException.class, () -> projectMemberService.addProjectMember(projectMemberRequestDto));
    }

    @Test
    public void addProjectMember_WhenProjectNotExists_ThrowProjectNotFoundException() {
        //Arrange
        ProjectMemberRequestDto projectMemberRequestDto = new ProjectMemberRequestDto();
        projectMemberRequestDto.setUserId(UUID.randomUUID());
        projectMemberRequestDto.setProjectId(UUID.randomUUID());
        when(projectMemberRepository.isUserExists(projectMemberRequestDto.getUserId())).thenReturn(true);
        when(projectMemberRepository.isProjectExists(projectMemberRequestDto.getProjectId())).thenReturn(false);
        //Act
        //Assert
        assertThrows(ProjectNotFoundException.class, ()-> projectMemberService.addProjectMember(projectMemberRequestDto));
    }

    @Test
    public void addProjectMember_WhenUserExistsInProject_ThrowUserAlreadyExistsInProject() {
        //Arrange
        ProjectMemberRequestDto projectMemberRequestDto = new ProjectMemberRequestDto();
        projectMemberRequestDto.setUserId(UUID.randomUUID());
        projectMemberRequestDto.setProjectId(UUID.randomUUID());
        when(projectMemberRepository.isUserExists(projectMemberRequestDto.getUserId())).thenReturn(true);
        when(projectMemberRepository.isProjectExists(projectMemberRequestDto.getProjectId())).thenReturn(true);
        when(projectMemberRepository.isUserExistsInProject(projectMemberRequestDto.getProjectId(), projectMemberRequestDto.getUserId())).thenReturn(true);
        //Act
        //Assert
        assertThrows(UserAlreadyExistsInProject.class, ()-> projectMemberService.addProjectMember(projectMemberRequestDto));
    }

    @Test
    public void getAllProjectMembers_ReturnsAllProjectMembers() {
        //Arrange
        ProjectMember projectMember = new ProjectMember();
        projectMember.setUserId(UUID.randomUUID());
        List<ProjectMember> projectMemberList = List.of(projectMember);
        List<ProjectMemberResponseDto> projectMemberResponseDtoList = List.of(modelMapper.map(projectMember, ProjectMemberResponseDto.class));
        when(projectMemberRepository.findByIsActiveTrue()).thenReturn(projectMemberList);

        //Act
        CustomResponseDto<List<ProjectMemberResponseDto>> response = projectMemberService.getAllProjectMembers();

        //Assert
        assertThat(response.getData().get(0).getUserId()).isEqualTo(projectMemberResponseDtoList.get(0).getUserId());

    }

    @Test
    public void getProjectMembersByProjectId_ReturnsAllProjectMembers() {
        //Arrange
        UUID projectId = UUID.randomUUID();
        ProjectMember projectMember = new ProjectMember();
        projectMember.setProjectId(projectId);
        when(projectMemberRepository.getProjectMembersByProjectId(projectId)).thenReturn(List.of(projectMember));
        List<ProjectMemberResponseDto> projectMemberResponseDtoList = List.of(modelMapper.map(projectMember, ProjectMemberResponseDto.class));
        //Act
        CustomResponseDto<List<ProjectMemberResponseDto>> response = projectMemberService.getProjectMembersByProjectId(projectId);

        //Assert
        assertThat(response.getData().get(0).getProjectId()).isEqualTo(projectMemberResponseDtoList.get(0).getProjectId());
    }

    @Test
    public void getUserMembershipsByUserId_ReturnsAllProjectMembers() {
        //Arrange
        UUID userId = UUID.randomUUID();
        ProjectMember projectMember = new ProjectMember();
        projectMember.setUserId(userId);
        when(projectMemberRepository.getMembershipInfosByUserId(userId)).thenReturn(List.of(projectMember));
        List<ProjectMemberResponseDto> projectMemberResponseDtoList = List.of(modelMapper.map(projectMember, ProjectMemberResponseDto.class));
        //Act
        CustomResponseDto<List<ProjectMemberResponseDto>> response = projectMemberService.getUserMembershipsByUserId(userId);

        //Assert
        assertThat(response.getData().get(0).getUserId()).isEqualTo(projectMemberResponseDtoList.get(0).getUserId());
    }

    @Test
    public void checkIfUserExistsInProject_ReturnsTrue() {
        //Arrange
        when(projectMemberRepository.isUserExistsInProject(any(UUID.class),any(UUID.class))).thenReturn(true);
        //Act
        boolean response = projectMemberService.checkIfUserExistsInProject(UUID.randomUUID(), UUID.randomUUID());
        //Assert
        assertThat(response).isTrue();
    }

    @Test
    public void removeMembership_ReturnsNoContent() {
        //Arrange
        UUID projectMemberId = UUID.randomUUID();
        ProjectMember projectMember = new ProjectMember();
        projectMember.setProjectId(projectMemberId);
        when(projectMemberRepository.findByIdAndIsActiveTrue(projectMemberId)).thenReturn(projectMember);
        //Act
        CustomResponseDto response = projectMemberService.removeMembership(projectMemberId);
        //Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    public void removeMembership_WhenProjectNotExists_ThrowProjectMemberNotFoundException() {
        //Arrange
        when(projectMemberRepository.findByIdAndIsActiveTrue(any(UUID.class))).thenReturn(null);
        //Act
        //Assert
        assertThrows(ProjectMemberNotFound.class, ()-> projectMemberService.removeMembership(UUID.randomUUID()));
    }
}
