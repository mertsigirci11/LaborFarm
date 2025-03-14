package com.laborfarm.core_app.service.implementation;

import com.laborfarm.core_app.entity.Department;
import com.laborfarm.core_app.entity.User;
import com.laborfarm.core_app.repository.DepartmentRepository;
import com.laborfarm.core_app.service.DepartmentService;
import com.laborfarm.core_app.service.dto.CustomResponseDto;
import com.laborfarm.core_app.service.dto.DepartmentDto;
import com.laborfarm.core_app.service.exception.department.DepartmentAlreadyExistsException;
import com.laborfarm.core_app.service.exception.department.DepartmentNotActiveException;
import com.laborfarm.core_app.service.exception.department.DepartmentNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public DepartmentServiceImpl(DepartmentRepository departmentRepository, ModelMapper modelMapper) {
        this.departmentRepository = departmentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CustomResponseDto<DepartmentDto> addDepartment(DepartmentDto departmentDto) {

        //Convert to Entity
        Department department = convertToEntity(departmentDto);

        //Saving
        department.setActive(true);
        Department savedDepartment = departmentRepository.save(department);

        return CustomResponseDto.success(HttpStatus.CREATED.value(), convertToDto(savedDepartment));
    }

    @Override
    public CustomResponseDto<List<DepartmentDto>> getAllDepartments() {
        List<DepartmentDto> departmentDtoList = departmentRepository.findByIsActiveTrue()
                .stream()
                .map(this::convertToDto)
                .toList();

        return CustomResponseDto.success(HttpStatus.OK.value(), departmentDtoList);
    }

    @Override
    public CustomResponseDto<DepartmentDto> getDepartmentById(UUID id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(DepartmentNotFoundException::new);

        if (!department.isActive()) {
            throw new DepartmentNotActiveException();
        }

        return CustomResponseDto.success(HttpStatus.OK.value(), convertToDto(department));
    }

    @Override
    public CustomResponseDto<DepartmentDto> updateDepartment(DepartmentDto departmentDto) {
        Department department = departmentRepository.findByIdAndIsActiveTrue(departmentDto.getId());

        if (department == null) {
            throw new DepartmentNotFoundException();
        }

        department.setName(departmentDto.getName());
        department.setUpdatedAt(new Date());
        department = departmentRepository.save(department);

        return CustomResponseDto.success(HttpStatus.OK.value(), convertToDto(departmentRepository.save(department)));
    }

    @Override
    public CustomResponseDto deleteDepartment(UUID id) {
        Department department = departmentRepository.findByIdAndIsActiveTrue(id);
        if (department == null) {
            throw new DepartmentNotFoundException();
        }
        department.setUpdatedAt(new Date());
        department.setActive(false);
        departmentRepository.save(department);

        return CustomResponseDto.success(HttpStatus.OK.value());
    }

    //Helper Methods
    public DepartmentDto convertToDto(Department department) {
        return modelMapper.map(department, DepartmentDto.class);
    }

    public Department convertToEntity(DepartmentDto departmentDto) {
        return modelMapper.map(departmentDto, Department.class);
    }
}
