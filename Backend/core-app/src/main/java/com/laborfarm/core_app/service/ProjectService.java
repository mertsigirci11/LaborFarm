package com.laborfarm.core_app.service;

import com.laborfarm.core_app.service.dto.CustomResponseDto;
import com.laborfarm.core_app.service.dto.ProjectDto;

import java.util.List;
import java.util.UUID;

public interface ProjectService {
    CustomResponseDto<ProjectDto> addProject(ProjectDto projectDto);
    CustomResponseDto<List<ProjectDto>> getAllProjects();
    CustomResponseDto<ProjectDto> getProjectById(UUID id);
    CustomResponseDto<ProjectDto> updateProject(ProjectDto projectDto);
    CustomResponseDto deleteProject(UUID id);
}
