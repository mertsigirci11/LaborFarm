package com.laborfarm.core_app.controller;

import com.laborfarm.core_app.service.ProjectService;
import com.laborfarm.core_app.service.dto.CustomResponseDto;
import com.laborfarm.core_app.service.dto.ProjectRequestDto;
import com.laborfarm.core_app.service.dto.ProjectResponseDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/v1")
    public ResponseEntity<CustomResponseDto<List<ProjectResponseDto>>> getAllProjects() {
        CustomResponseDto<List<ProjectResponseDto>> responseDto = projectService.getAllProjects();
        return new ResponseEntity<>(responseDto, HttpStatusCode.valueOf(responseDto.getStatusCode()));
    }

    @PostMapping("/v1")
    public ResponseEntity<CustomResponseDto<ProjectResponseDto>> createProject(@RequestBody @Valid ProjectRequestDto projectRequestDto) {
        CustomResponseDto<ProjectResponseDto> response = projectService.addProject(projectRequestDto);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @GetMapping("/v1/{id}")
    public ResponseEntity<CustomResponseDto<ProjectResponseDto>> getProjectById(@PathVariable UUID id) {
        CustomResponseDto<ProjectResponseDto> response = projectService.getProjectById(id);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @PutMapping("/v1")
    public ResponseEntity<CustomResponseDto<ProjectResponseDto>> updateProject(@RequestBody @Valid ProjectRequestDto projectRequestDto) {
        CustomResponseDto<ProjectResponseDto> response = projectService.updateProject(projectRequestDto);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @DeleteMapping("/v1/{id}")
    public ResponseEntity<CustomResponseDto> deleteProject(@PathVariable UUID id) {
        CustomResponseDto<ProjectResponseDto> response = projectService.deleteProject(id);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }
}
