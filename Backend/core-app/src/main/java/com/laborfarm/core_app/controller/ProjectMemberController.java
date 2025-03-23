package com.laborfarm.core_app.controller;

import com.laborfarm.core_app.service.ProjectMemberService;
import com.laborfarm.core_app.service.dto.CustomResponseDto;
import com.laborfarm.core_app.service.dto.ProjectMemberRequestDto;
import com.laborfarm.core_app.service.dto.ProjectMemberResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/projectmembers")
public class ProjectMemberController {
    private final ProjectMemberService projectMemberService;

    public ProjectMemberController(ProjectMemberService projectMemberService) {
        this.projectMemberService = projectMemberService;
    }

    @GetMapping("/v1")
    public ResponseEntity<CustomResponseDto<List<ProjectMemberResponseDto>>> getAllProjectMembers() {
        CustomResponseDto<List<ProjectMemberResponseDto>> response = projectMemberService.getAllProjectMembers();
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @GetMapping("/v1/{projectid}")
    public ResponseEntity<CustomResponseDto<List<ProjectMemberResponseDto>>> getProjectMembersByProjectId(@PathVariable UUID projectid) {
        CustomResponseDto<List<ProjectMemberResponseDto>> response = projectMemberService.getProjectMembersByProjectId(projectid);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @GetMapping("/v1/user/{id}")
    public ResponseEntity<CustomResponseDto<List<ProjectMemberResponseDto>>> getUserMembershipsByUserId(@PathVariable UUID id) {
        CustomResponseDto<List<ProjectMemberResponseDto>> response = projectMemberService.getUserMembershipsByUserId(id);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @GetMapping("/v1/check/user")
    public ResponseEntity checkIfUserExistsInProject(@RequestBody ProjectMemberRequestDto projectMemberRequestDto) {
        boolean response = projectMemberService
                .checkIfUserExistsInProject(projectMemberRequestDto.getProjectId(),projectMemberRequestDto.getUserId());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/v1")
    public ResponseEntity<CustomResponseDto<ProjectMemberResponseDto>> createProjectMember(@RequestBody ProjectMemberRequestDto projectMemberRequestDto) {
        CustomResponseDto<ProjectMemberResponseDto> response = projectMemberService.addProjectMember(projectMemberRequestDto);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @DeleteMapping("/v1/{id}")
    public ResponseEntity<CustomResponseDto> deleteProjectMember(@PathVariable UUID id) {
        CustomResponseDto response = projectMemberService.removeMembership(id);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }
}
