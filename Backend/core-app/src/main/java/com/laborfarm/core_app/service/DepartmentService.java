package com.laborfarm.core_app.service;

import com.laborfarm.core_app.service.dto.CustomResponseDto;
import com.laborfarm.core_app.service.dto.DepartmentDto;

import java.util.List;
import java.util.UUID;

public interface DepartmentService {
    CustomResponseDto<DepartmentDto> addDepartment(DepartmentDto departmentDto);
    CustomResponseDto<List<DepartmentDto>> getAllDepartments();
    CustomResponseDto<DepartmentDto> getDepartmentById(UUID id);
    CustomResponseDto<DepartmentDto> updateDepartment(DepartmentDto departmentDto);
    CustomResponseDto deleteDepartment(UUID id);
}