package com.laborfarm.core_app.controller;

import com.laborfarm.core_app.service.ProjectService;
import com.laborfarm.core_app.service.dto.CustomResponseDto;
import com.laborfarm.core_app.service.dto.ProjectRequestDto;
import com.laborfarm.core_app.service.dto.ProjectResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(MockitoExtension.class)
class ProjectControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProjectService projectService;

    @InjectMocks
    private ProjectController projectController;

    private UUID projectId;
    private ProjectResponseDto projectResponseDto;
    private CustomResponseDto<ProjectResponseDto> responseDto;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(projectController).build();

        projectId = UUID.randomUUID();
        projectResponseDto = new ProjectResponseDto();
        projectResponseDto.setId(projectId);
        projectResponseDto.setName("Test Project");

        responseDto = CustomResponseDto.success(200, projectResponseDto);
    }

    @Test
    void getAllProjects_ReturnsProjectList() throws Exception {
        List<ProjectResponseDto> projectList = List.of(projectResponseDto);
        CustomResponseDto<List<ProjectResponseDto>> listResponse = CustomResponseDto.success(200, projectList);

        when(projectService.getAllProjects()).thenReturn(listResponse);

        mockMvc.perform(get("/api/projects/v1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].name").value("Test Project"));
    }

    @Test
    void getProjectById_ReturnsProject() throws Exception {
        when(projectService.getProjectById(projectId)).thenReturn(responseDto);

        mockMvc.perform(get("/api/projects/v1/{id}", projectId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("Test Project"));
    }

    @Test
    void createProject_ReturnsCreatedProject() throws Exception {
        ProjectRequestDto requestDto = new ProjectRequestDto();
        requestDto.setName("New Project");

        when(projectService.addProject(any())).thenReturn(responseDto);

        mockMvc.perform(post("/api/projects/v1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"New Project\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("Test Project"));
    }

    @Test
    void updateProject_ReturnsUpdatedProject() throws Exception {
        ProjectRequestDto requestDto = new ProjectRequestDto();
        requestDto.setId(projectId);
        requestDto.setName("Updated Project");

        when(projectService.updateProject(any())).thenReturn(responseDto);

        mockMvc.perform(put("/api/projects/v1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": \"" + projectId + "\", \"name\": \"Updated Project\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("Test Project"));
    }

    @Test
    void deleteProject_ReturnsSuccessResponse() throws Exception {
        CustomResponseDto<Void> deleteResponse = CustomResponseDto.success(200, null);
        when(projectService.deleteProject(projectId)).thenReturn(deleteResponse);

        mockMvc.perform(delete("/api/projects/v1/{id}", projectId))
                .andExpect(status().isOk());
    }
}
