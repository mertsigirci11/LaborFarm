package com.laborfarm.core_app.controller;

import com.laborfarm.core_app.service.ProjectMemberService;
import com.laborfarm.core_app.service.dto.CustomResponseDto;
import com.laborfarm.core_app.service.dto.ProjectMemberRequestDto;
import com.laborfarm.core_app.service.dto.ProjectMemberResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectMemberControllerTest {

    @InjectMocks
    private ProjectMemberController projectMemberController;

    @Mock
    private ProjectMemberService projectMemberService;

    private UUID sampleProjectId;
    private UUID sampleUserId;
    private UUID sampleMembershipId;
    private ProjectMemberRequestDto requestDto;
    private ProjectMemberResponseDto responseDto;
    private List<ProjectMemberResponseDto> responseList;

    @BeforeEach
    void setUp() {
        sampleProjectId = UUID.randomUUID();
        sampleUserId = UUID.randomUUID();
        sampleMembershipId = UUID.randomUUID();

        requestDto = new ProjectMemberRequestDto();
        requestDto.setProjectId(sampleProjectId);
        requestDto.setUserId(sampleUserId);

        responseDto = new ProjectMemberResponseDto();
        responseDto.setId(sampleMembershipId);
        responseDto.setProjectId(sampleProjectId);
        responseDto.setUserId(sampleUserId);
        responseDto.setProjectName("Test Project");

        responseList = new ArrayList<>();
        responseList.add(responseDto);
    }

    @Test
    void testGetAllProjectMembers() {
        CustomResponseDto<List<ProjectMemberResponseDto>> mockResponse = CustomResponseDto.success(200, responseList);
        when(projectMemberService.getAllProjectMembers()).thenReturn(mockResponse);

        ResponseEntity<CustomResponseDto<List<ProjectMemberResponseDto>>> response = projectMemberController.getAllProjectMembers();

        assertEquals(200, response.getBody().getStatusCode());
        assertEquals(1, response.getBody().getData().size());
    }

    @Test
    void testGetProjectMembersByProjectId() {
        CustomResponseDto<List<ProjectMemberResponseDto>> mockResponse = CustomResponseDto.success(200, responseList);
        when(projectMemberService.getProjectMembersByProjectId(sampleProjectId)).thenReturn(mockResponse);

        ResponseEntity<CustomResponseDto<List<ProjectMemberResponseDto>>> response = projectMemberController.getProjectMembersByProjectId(sampleProjectId);

        assertEquals(200, response.getBody().getStatusCode());
        assertEquals(sampleProjectId, response.getBody().getData().get(0).getProjectId());
    }

    @Test
    void testGetUserMembershipsByUserId() {
        CustomResponseDto<List<ProjectMemberResponseDto>> mockResponse = CustomResponseDto.success(200, responseList);
        when(projectMemberService.getUserMembershipsByUserId(sampleUserId)).thenReturn(mockResponse);

        ResponseEntity<CustomResponseDto<List<ProjectMemberResponseDto>>> response = projectMemberController.getUserMembershipsByUserId(sampleUserId);

        assertEquals(200, response.getBody().getStatusCode());
        assertEquals(sampleUserId, response.getBody().getData().get(0).getUserId());
    }

    @Test
    void testCheckIfUserExistsInProject() {
        when(projectMemberService.checkIfUserExistsInProject(sampleProjectId, sampleUserId)).thenReturn(true);

        ResponseEntity<Boolean> response = projectMemberController.checkIfUserExistsInProject(requestDto);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(true, response.getBody());
    }

    @Test
    void testCreateProjectMember() {
        CustomResponseDto<ProjectMemberResponseDto> mockResponse = CustomResponseDto.success(201, responseDto);
        when(projectMemberService.addProjectMember(requestDto)).thenReturn(mockResponse);

        ResponseEntity<CustomResponseDto<ProjectMemberResponseDto>> response = projectMemberController.createProjectMember(requestDto);

        assertEquals(201, response.getBody().getStatusCode());
        assertEquals(sampleMembershipId, response.getBody().getData().getId());
    }

    @Test
    void testDeleteProjectMember() {
        CustomResponseDto<Void> mockResponse = CustomResponseDto.success(204);
        when(projectMemberService.removeMembership(sampleMembershipId)).thenReturn(mockResponse);

        ResponseEntity<CustomResponseDto> response = projectMemberController.deleteProjectMember(sampleMembershipId);

        assertEquals(204, response.getBody().getStatusCode());
    }
}