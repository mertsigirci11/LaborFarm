package com.laborfarm.core_app.service.implementation;

import com.laborfarm.core_app.entity.project.Project;
import com.laborfarm.core_app.repository.ProjectRepository;
import com.laborfarm.core_app.service.ProjectService;
import com.laborfarm.core_app.service.dto.CustomResponseDto;
import com.laborfarm.core_app.service.dto.ProjectDto;
import com.laborfarm.core_app.service.exception.project.ProjectNotActiveException;
import com.laborfarm.core_app.service.exception.project.ProjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository, ModelMapper modelMapper) {
        this.projectRepository = projectRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CustomResponseDto<ProjectDto> addProject(ProjectDto projectDto) {

        //Convert to Entity
        Project project = convertToEntity(projectDto);

        //Saving
        project.setActive(true);
        project.setCreatedAt(new Date());
        Project savedProject = projectRepository.save(project);

        return CustomResponseDto.success(HttpStatus.CREATED.value(), convertToDto(savedProject));
    }

    @Override
    public CustomResponseDto<List<ProjectDto>> getAllProjects() {
        List<ProjectDto> projectDtoListDtoList = projectRepository.findByIsActiveTrue()
                .stream()
                .map(this::convertToDto)
                .toList();

        return CustomResponseDto.success(HttpStatus.OK.value(), projectDtoListDtoList);
    }

    @Override
    public CustomResponseDto<ProjectDto> getProjectById(UUID id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(ProjectNotFoundException::new);

        if (!project.isActive()) {
            throw new ProjectNotActiveException();
        }

        return CustomResponseDto.success(HttpStatus.OK.value(), convertToDto(project));
    }

    @Override
    public CustomResponseDto<ProjectDto> updateProject(ProjectDto projectDto) {
        Project project = projectRepository.findByIdAndIsActiveTrue(projectDto.getId());

        if (project == null) {
            throw new ProjectNotFoundException();
        }

        project.setName(projectDto.getName());
        project.setDescription(projectDto.getDescription());
        project.setTitle(projectDto.getTitle());
        project.setUpdatedAt(new Date());
        project = projectRepository.save(project);

        return CustomResponseDto.success(HttpStatus.OK.value(), convertToDto(project));
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
    public ProjectDto convertToDto(Project project) {
        return modelMapper.map(project, ProjectDto.class);
    }

    public Project convertToEntity(ProjectDto projectDto) {
        return modelMapper.map(projectDto, Project.class);
    }
}