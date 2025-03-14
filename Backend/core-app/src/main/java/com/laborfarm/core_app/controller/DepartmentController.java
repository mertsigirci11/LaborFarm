package com.laborfarm.core_app.controller;

import com.laborfarm.core_app.service.DepartmentService;
import com.laborfarm.core_app.service.dto.CustomResponseDto;
import com.laborfarm.core_app.service.dto.DepartmentDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {
    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/v1")
    public ResponseEntity<CustomResponseDto<List<DepartmentDto>>> getAllDepartments() {
        CustomResponseDto<List<DepartmentDto>> response = departmentService.getAllDepartments();
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @PostMapping("/v1")
    public ResponseEntity<CustomResponseDto<DepartmentDto>> createDepartment(@RequestBody @Valid DepartmentDto departmentDto) {
        CustomResponseDto<DepartmentDto> response = departmentService.addDepartment(departmentDto);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @GetMapping("/v1/{id}")
    public ResponseEntity<CustomResponseDto<DepartmentDto>> getDepartmentById(@PathVariable UUID id) {
        CustomResponseDto<DepartmentDto> response = departmentService.getDepartmentById(id);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @PutMapping("/v1")
    public ResponseEntity<CustomResponseDto<DepartmentDto>> updateDepartment(@RequestBody @Valid DepartmentDto departmentDto) {
        CustomResponseDto<DepartmentDto> response = departmentService.updateDepartment(departmentDto);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @DeleteMapping("/v1/{id}")
    public ResponseEntity<CustomResponseDto> deleteDepartment(@PathVariable UUID id) {
        CustomResponseDto response = departmentService.deleteDepartment(id);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }
}
