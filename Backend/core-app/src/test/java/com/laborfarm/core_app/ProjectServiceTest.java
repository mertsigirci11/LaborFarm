package com.laborfarm.core_app;

import com.laborfarm.core_app.entity.Department;
import com.laborfarm.core_app.entity.project.Project;
import com.laborfarm.core_app.repository.DepartmentRepository;
import com.laborfarm.core_app.repository.ProjectRepository;
import com.laborfarm.core_app.service.ProjectService;
import com.laborfarm.core_app.service.dto.CustomResponseDto;
import com.laborfarm.core_app.service.dto.ProjectRequestDto;
import com.laborfarm.core_app.service.dto.ProjectResponseDto;
import com.laborfarm.core_app.service.exception.department.DepartmentNotFoundException;
import com.laborfarm.core_app.service.exception.project.ProjectNotActiveException;
import com.laborfarm.core_app.service.exception.project.ProjectNotFoundException;
import com.laborfarm.core_app.service.exception.status.NoMatchingStatusException;
import com.laborfarm.core_app.service.implementation.ProjectServiceImpl;
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
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private ProjectServiceImpl projectService;

    private ModelMapper modelMapper = new ModelMapper();

    @BeforeEach
    void setUp() {
        modelMapper.addMappings(new PropertyMap<Project, ProjectResponseDto>() {
            @Override
            protected void configure() {
                map(source.getDepartmentId(), destination.getDepartmentId());
                map(source.getStatusId(), destination.getStatusId());
            }
        });
        projectService = new ProjectServiceImpl(projectRepository, departmentRepository, modelMapper);
    }

    @Test
    public void addProject_ReturnsNewProject() {
        //Arrange
        UUID departmentId = UUID.randomUUID();
        UUID projectId = UUID.randomUUID();
        ProjectRequestDto projectRequestDto = new ProjectRequestDto();
        projectRequestDto.setDepartmentId(departmentId);
        projectRequestDto.setStatusId(1);
        Department department = new Department();

        Project project = modelMapper.map(projectRequestDto, Project.class);
        project.setId(projectId);

        when(departmentRepository.findByIdAndIsActiveTrue(departmentId)).thenReturn(department);
        when(projectRepository.save(project)).thenReturn(project);

        ProjectResponseDto responseDto = modelMapper.map(project, ProjectResponseDto.class);
        //Act
        CustomResponseDto<ProjectResponseDto> response = projectService.addProject(projectRequestDto);
        //Assert
        assertThat(response.getData().getId()).isEqualTo(responseDto.getId());
    }

    @Test
    public void addProject_WhenDepartmentIsNull_ThrowsDepartmentNotFoundException(){
        //Arrange
        ProjectRequestDto projectRequestDto = new ProjectRequestDto();
        projectRequestDto.setDepartmentId(UUID.randomUUID());
        when(departmentRepository.findByIdAndIsActiveTrue(any(UUID.class))).thenReturn(null);
        //Act
        //Assert
        assertThrows(DepartmentNotFoundException.class, () -> projectService.addProject(projectRequestDto));
    }

    @Test
    public void addProject_WhenStatusIsNotMatch_ThrowsNoMatchingStatusException(){
        //Arrange
        ProjectRequestDto projectRequestDto = new ProjectRequestDto();
        projectRequestDto.setDepartmentId(UUID.randomUUID());
        projectRequestDto.setStatusId(4);//Options 1 to 3
        when(departmentRepository.findByIdAndIsActiveTrue(any(UUID.class))).thenReturn(new Department());
        //Act
        //Assert
        assertThrows(NoMatchingStatusException.class, ()-> projectService.addProject(projectRequestDto));
    }

    @Test
    public void getAllProjects_ReturnsAllProjects() {
        //Arrange
        Project project = new Project();
        project.setId(UUID.randomUUID());
        List<Project> projectList = List.of(project);
        List<ProjectResponseDto> projectResponseDtoList = List.of(modelMapper.map(project, ProjectResponseDto.class));
        when(projectRepository.findByIsActiveTrue()).thenReturn(projectList);
        //Act
        CustomResponseDto<List<ProjectResponseDto>> response = projectService.getAllProjects();
        //Assert
        assertThat(response.getData().get(0).getId()).isEqualTo(projectResponseDtoList.get(0).getId());
    }

    @Test
    public void getProjectById_ReturnsProject() {
        //Arrange
        UUID projectId = UUID.randomUUID();
        UUID departmentId = UUID.randomUUID();
        Project project = new Project();
        project.setDepartmentId(departmentId);
        project.setId(projectId);
        project.setStatusId(1);
        Department department = new Department();
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(departmentRepository.findByIdAndIsActiveTrue(departmentId)).thenReturn(department);
        ProjectResponseDto responseDto = modelMapper.map(project, ProjectResponseDto.class);
        //Act
        CustomResponseDto<ProjectResponseDto> response = projectService.getProjectById(projectId);
        //Assert
        assertThat(response.getData().getId()).isEqualTo(responseDto.getId());
    }

    @Test
    public void getProjectById_WhenProjectNotFound_ThrowsProjectNotFoundException() {
        //Arrange
        when(projectRepository.findById(any(UUID.class))).thenReturn(Optional.empty());
        //Act
        //Assert
        assertThrows(ProjectNotFoundException.class, () -> projectService.getProjectById(UUID.randomUUID()));
    }

    @Test
    public void getProjectById_WhenProjectIsNotActive_ThrowsProjectNotActiveException() {
        //Arrange
        UUID projectId = UUID.randomUUID();
        Project project = new Project();
        project.setId(projectId);
        project.setActive(false);
        when(projectRepository.findById(any(UUID.class))).thenReturn(Optional.of(project));
        //Act
        //Assert
        assertThrows(ProjectNotActiveException.class, () -> projectService.getProjectById(projectId));
    }

    @Test
    public void updateProject_ReturnsUpdatedProject() {
        //Arrange
        UUID projectId = UUID.randomUUID();
        UUID departmentId = UUID.randomUUID();
        ProjectRequestDto projectRequestDto = new ProjectRequestDto();
        projectRequestDto.setId(projectId);
        projectRequestDto.setStatusId(2);
        projectRequestDto.setDepartmentId(departmentId);
        Department department = new Department();
        department.setId(departmentId);
        Project project = modelMapper.map(projectRequestDto, Project.class);
        project.setStatusId(1);

        when(projectRepository.findByIdAndIsActiveTrue(projectId)).thenReturn(project);
        when(projectRepository.save(project)).thenReturn(project);
        when(departmentRepository.findByIdAndIsActiveTrue(departmentId)).thenReturn(department);

        CustomResponseDto<ProjectResponseDto> response = projectService.updateProject(projectRequestDto);

        assertThat(response.getData().getStatusId()).isEqualTo(2);

    }

    @Test
    public void updateProject_WhenProjectNotFound_ThrowsProjectNotFoundException() {
        //Arrange
        ProjectRequestDto projectRequestDto = new ProjectRequestDto();
        projectRequestDto.setId(UUID.randomUUID());
        when(projectRepository.findByIdAndIsActiveTrue(any(UUID.class))).thenReturn(null);
        //Act
        //Assert
        assertThrows(ProjectNotFoundException.class, () -> projectService.updateProject(projectRequestDto));
    }

    @Test
    public void deleteProject_ReturnsOk() {
        //Arrange
        UUID projectId = UUID.randomUUID();
        Project project = new Project();
        project.setId(projectId);
        project.setActive(true);

        Project savedProject = new Project();
        savedProject.setId(projectId);
        savedProject.setActive(false);

        when(projectRepository.findByIdAndIsActiveTrue(projectId)).thenReturn(project);
        when(projectRepository.save(project)).thenReturn(project);
        //Act
        CustomResponseDto response = projectService.deleteProject(projectId);
        //Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(project.isActive()).isFalse();
    }

    @Test
    public void deleteProject_WhenProjectNotFound_ThrowsProjectNotFoundException() {
        //Arrange
        UUID projectId = UUID.randomUUID();
        when(projectRepository.findByIdAndIsActiveTrue(projectId)).thenReturn(null);
        //Act
        //Assert
        assertThrows(ProjectNotFoundException.class, () -> projectService.deleteProject(projectId));
    }
}
