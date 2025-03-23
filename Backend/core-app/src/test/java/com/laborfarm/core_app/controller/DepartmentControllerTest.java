package com.laborfarm.core_app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.laborfarm.core_app.service.DepartmentService;
import com.laborfarm.core_app.service.dto.CustomResponseDto;
import com.laborfarm.core_app.service.dto.DepartmentDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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

@ExtendWith(MockitoExtension.class)
class DepartmentControllerTest {

    private MockMvc mockMvc;

    @Mock
    private DepartmentService departmentService;

    @InjectMocks
    private DepartmentController departmentController;

    private UUID departmentId;
    private DepartmentDto departmentDto;
    private CustomResponseDto<DepartmentDto> responseDto;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(departmentController).build();

        departmentId = UUID.randomUUID();
        departmentDto = new DepartmentDto();
        departmentDto.setId(departmentId);
        departmentDto.setName("IT Department");

        responseDto = CustomResponseDto.success(200, departmentDto);
    }

    @Test
    void getAllDepartments_ReturnsDepartmentList() throws Exception {
        List<DepartmentDto> departmentList = List.of(departmentDto);
        CustomResponseDto<List<DepartmentDto>> listResponse = CustomResponseDto.success(200, departmentList);

        when(departmentService.getAllDepartments()).thenReturn(listResponse);

        mockMvc.perform(get("/api/departments/v1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].name").value("IT Department"));
    }

    @Test
    void getDepartmentById_ReturnsDepartment() throws Exception {
        when(departmentService.getDepartmentById(departmentId)).thenReturn(responseDto);

        mockMvc.perform(get("/api/departments/v1/{id}", departmentId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(departmentId.toString()))
                .andExpect(jsonPath("$.data.name").value("IT Department"));
    }

    @Test
    void createDepartment_ReturnsCreatedDepartment() throws Exception {
        when(departmentService.addDepartment(any(DepartmentDto.class))).thenReturn(responseDto);

        mockMvc.perform(post("/api/departments/v1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(departmentDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("IT Department"));
    }

    @Test
    void updateDepartment_ReturnsUpdatedDepartment() throws Exception {
        when(departmentService.updateDepartment(any(DepartmentDto.class))).thenReturn(responseDto);

        mockMvc.perform(put("/api/departments/v1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(departmentDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("IT Department"));
    }

    @Test
    void deleteDepartment_ReturnsSuccessResponse() throws Exception {
        CustomResponseDto<Void> deleteResponse = CustomResponseDto.success(200, null);
        when(departmentService.deleteDepartment(departmentId)).thenReturn(deleteResponse);

        mockMvc.perform(delete("/api/departments/v1/{id}", departmentId))
                .andExpect(status().isOk());
    }
}

