package com.laborfarm.core_app.controller;

import com.laborfarm.core_app.service.ProjectService;
import com.laborfarm.core_app.service.dto.CustomResponseDto;
import com.laborfarm.core_app.service.dto.ProjectDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<CustomResponseDto<List<ProjectDto>>> getAllProjects() {
        CustomResponseDto<List<ProjectDto>> responseDto = projectService.getAllProjects();
        return new ResponseEntity<>(responseDto, HttpStatusCode.valueOf(responseDto.getStatusCode()));
    }

    @PostMapping("/v1")
    public ResponseEntity<CustomResponseDto<ProjectDto>> createProject(@RequestBody @Valid ProjectDto projectDto) {
        CustomResponseDto<ProjectDto> response = projectService.addProject(projectDto);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @GetMapping("/v1/{id}")
    public ResponseEntity<CustomResponseDto<ProjectDto>> getProjectById(@PathVariable UUID id) {
        CustomResponseDto<ProjectDto> response = projectService.getProjectById(id);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @PutMapping("/v1")
    public ResponseEntity<CustomResponseDto<ProjectDto>> updateProject(@RequestBody @Valid ProjectDto projectDto) {
        CustomResponseDto<ProjectDto> response = projectService.updateProject(projectDto);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @DeleteMapping("/v1/{id}")
    public ResponseEntity<CustomResponseDto> deleteProject(@PathVariable UUID id) {
        CustomResponseDto<ProjectDto> response = projectService.deleteProject(id);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }
}
