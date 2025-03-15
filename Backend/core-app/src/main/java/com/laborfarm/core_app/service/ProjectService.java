package com.laborfarm.core_app.service;

import com.laborfarm.core_app.service.dto.CustomResponseDto;
import com.laborfarm.core_app.service.dto.ProjectRequestDto;
import com.laborfarm.core_app.service.dto.ProjectResponseDto;

import java.util.List;
import java.util.UUID;

public interface ProjectService {
    CustomResponseDto<ProjectResponseDto> addProject(ProjectRequestDto projectRequestDto);
    CustomResponseDto<List<ProjectResponseDto>> getAllProjects();
    CustomResponseDto<ProjectResponseDto> getProjectById(UUID id);
    CustomResponseDto<ProjectResponseDto> updateProject(ProjectRequestDto projectRequestDto);
    CustomResponseDto deleteProject(UUID id);
}
