package com.laborfarm.core_app.service.implementation;

import com.laborfarm.core_app.entity.Department;
import com.laborfarm.core_app.entity.project.Project;
import com.laborfarm.core_app.entity.project.Status;
import com.laborfarm.core_app.entity.project.StatusEntity;
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
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final DepartmentRepository departmentRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository, DepartmentRepository departmentRepository, ModelMapper modelMapper) {
        this.projectRepository = projectRepository;
        this.departmentRepository = departmentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CustomResponseDto<ProjectResponseDto> addProject(ProjectRequestDto projectRequestDto) {

        //Convert to Entity
        Project project = convertToEntity(projectRequestDto);

        //Department check
        Department department = departmentRepository.findByIdAndIsActiveTrue(projectRequestDto.getDepartmentId());
        if (department == null) {
            throw new DepartmentNotFoundException();
        }

        //Status check
        Status status = Arrays.stream(Status.values())
                .filter(s -> s.getValue() == projectRequestDto.getStatusId())
                .findFirst()
                .orElseThrow(NoMatchingStatusException::new);

        //Assigning values
        project.setActive(true);
        project.setCreatedAt(new Date());

        //Saving
        Project savedProject = projectRepository.save(project);

        //Response
        ProjectResponseDto response = convertToDto(savedProject);
        response.setDepartmentName(department.getName());
        response.setStatusId(status.getValue());
        response.setStatusName(status.name());

        return CustomResponseDto.success(HttpStatus.CREATED.value(), response);
    }

    @Override
    public CustomResponseDto<List<ProjectResponseDto>> getAllProjects() {
        List<ProjectResponseDto> projectDtoListDtoList = projectRepository.findByIsActiveTrue()
                .stream()
                .map(this::convertToDto)
                .toList();

        return CustomResponseDto.success(HttpStatus.OK.value(), projectDtoListDtoList);
    }

    @Override
    public CustomResponseDto<ProjectResponseDto> getProjectById(UUID id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(ProjectNotFoundException::new);

        if (!project.isActive()) {
            throw new ProjectNotActiveException();
        }

        //Convert to Dto
        ProjectResponseDto response = convertToDto(project);

        //Get the department & Fill the fields
        Department department = departmentRepository.findByIdAndIsActiveTrue(response.getDepartmentId());
        response.setDepartmentName(department.getName());
        response.setStatusName(Status.fromValue(response.getStatusId()).name());

        return CustomResponseDto.success(HttpStatus.OK.value(), response);
    }

    @Override
    public CustomResponseDto<ProjectResponseDto> updateProject(ProjectRequestDto projectRequestDto) {
        Project project = projectRepository.findByIdAndIsActiveTrue(projectRequestDto.getId());

        if (project == null) {
            throw new ProjectNotFoundException();
        }

        //Update the fields
        project.setName(projectRequestDto.getName());
        project.setDescription(projectRequestDto.getDescription());
        project.setTitle(projectRequestDto.getTitle());
        project.setUpdatedAt(new Date());
        project.setStatusId(projectRequestDto.getStatusId());
        project.setDepartmentId(projectRequestDto.getDepartmentId());
        project = projectRepository.save(project);

        //Dto
        ProjectResponseDto response = convertToDto(project);
        Department department = departmentRepository.findByIdAndIsActiveTrue(response.getDepartmentId());
        response.setDepartmentName(department.getName());
        response.setStatusName(Status.fromValue(response.getStatusId()).name());

        return CustomResponseDto.success(HttpStatus.OK.value(), response);
    }

    @Override
    public CustomResponseDto deleteProject(UUID id) {
        Project project = projectRepository.findByIdAndIsActiveTrue(id);

        if (project == null) {
            throw new ProjectNotFoundException();
        }

        project.setActive(false);
        project.setUpdatedAt(new Date());
        projectRepository.save(project);

        return CustomResponseDto.success(HttpStatus.OK.value());
    }

    //Helper Methods
    public ProjectResponseDto convertToDto(Project project) {
        return modelMapper.map(project, ProjectResponseDto.class);
    }

    public Project convertToEntity(ProjectRequestDto projectRequestDto) {
        return modelMapper.map(projectRequestDto, Project.class);
    }
}