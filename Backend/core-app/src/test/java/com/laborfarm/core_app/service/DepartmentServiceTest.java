package com.laborfarm.core_app.service;

import com.laborfarm.core_app.entity.Department;
import com.laborfarm.core_app.repository.DepartmentRepository;
import com.laborfarm.core_app.service.dto.CustomResponseDto;
import com.laborfarm.core_app.service.dto.DepartmentDto;
import com.laborfarm.core_app.service.exception.department.DepartmentNotActiveException;
import com.laborfarm.core_app.service.exception.department.DepartmentNotFoundException;
import com.laborfarm.core_app.service.implementation.DepartmentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private DepartmentServiceImpl departmentService;

    private ModelMapper modelMapper = new ModelMapper();

    @BeforeEach
    void setup() {
        departmentService = new DepartmentServiceImpl(departmentRepository, modelMapper);
    }

    @Test
    public void addDepartment_ReturnNewDepartment() {
        //Act
        DepartmentDto departmentDto1 = DepartmentDto.builder()
                .id(UUID.randomUUID())
                .name("Test Department1")
                .build();

        Department department1 = modelMapper.map(departmentDto1, Department.class);
        when(departmentRepository.save(department1)).thenReturn(department1);

        //Arrange
        CustomResponseDto<DepartmentDto> response = departmentService.addDepartment(departmentDto1);

        //Assert
        assertThat(response.getData().getId()).isEqualTo(department1.getId());
    }

    @Test
    public void getAllDepartments_ReturnAllDepartments() {
        //Act
        Department department1 = Department.builder()
                .name("Department1")
                .build();
        List<Department> departments = Arrays.asList(department1);

        DepartmentDto dto1 = modelMapper.map(department1, DepartmentDto.class);
        List<DepartmentDto> dtos = Arrays.asList(dto1);

        when(departmentRepository.findByIsActiveTrue()).thenReturn(departments);
        //Arrange
        CustomResponseDto<List<DepartmentDto>> response = departmentService.getAllDepartments();

        //Assert
        assertThat(response.getData()).isEqualTo(dtos);
    }

    @Test
    public void getDepartmentById_ReturnDepartment() {
        //Act
        UUID id = UUID.randomUUID();
        Department department = Department.builder().name("Department1").build();
        department.setId(id);

        when(departmentRepository.findByIdAndIsActiveTrue(id)).thenReturn(department);
        DepartmentDto dto = modelMapper.map(department, DepartmentDto.class);
        //Arrange
        CustomResponseDto<DepartmentDto> response = departmentService.getDepartmentById(id);

        //Assert
        assertThat(response.getData().getId()).isEqualTo(dto.getId());
    }

    @Test
    public void getDepartmentById_WhenDepartmentNotFound_ReturnDepartmentNotFoundException() {
        //Act
        when(departmentRepository.findByIdAndIsActiveTrue(any(UUID.class))).thenReturn(null);
        //Arrange
        //Assert
        assertThrows(DepartmentNotFoundException.class, () -> departmentService.getDepartmentById(UUID.randomUUID()));
    }

    @Test
    public void getDepartmentById_WhenDepartmentNotActive_ReturnDepartmentNotActiveException() {
        //Act
        Department department1 = Department.builder().name("Department1").build();
        department1.setActive(false);
        when(departmentRepository.findByIdAndIsActiveTrue(any(UUID.class))).thenReturn(department1);
        //Arrange
        //Assert
        assertThrows(DepartmentNotActiveException.class, () -> departmentService.getDepartmentById(UUID.randomUUID()));
    }

    @Test
    public void updateDepartment_ReturnUpdatedDepartment() {
        //Act
        UUID id = UUID.randomUUID();
        Department existingDepartment = Department.builder().name("Department1").build();
        existingDepartment.setId(id);
        existingDepartment.setActive(true);

        Department updatedDepartment = Department.builder().name("Department Updated").build();
        updatedDepartment.setId(id);
        DepartmentDto updatedDepartmentDto = DepartmentDto.builder().name("Department Updated").id(id).build();

        when(departmentRepository.findByIdAndIsActiveTrue(id)).thenReturn(existingDepartment);
        when(departmentRepository.save(existingDepartment)).thenReturn(updatedDepartment);

        //Arrange
        CustomResponseDto<DepartmentDto> response = departmentService.updateDepartment(updatedDepartmentDto);

        //Assert
        assertThat(response.getData().getId()).isEqualTo(existingDepartment.getId());
        assertThat(response.getData().getName()).isEqualTo(updatedDepartment.getName());
    }

    @Test
    public void updateDepartment_WhenDepartmentNotFound_ReturnDepartmentNotFoundException() {
        //Act
        DepartmentDto departmentDto = DepartmentDto.builder().id(UUID.randomUUID()).build();
        when(departmentRepository.findByIdAndIsActiveTrue(any(UUID.class))).thenReturn(null);
        //Arrange

        //Assert
        assertThrows(DepartmentNotFoundException.class, () -> departmentService.updateDepartment(departmentDto));
    }

    @Test
    public void deleteDepartment_ReturnSuccessResponse(){
        //Act
        UUID id = UUID.randomUUID();
        Department departmentBeforeDelete = Department.builder().name("Department1").build();
        departmentBeforeDelete.setId(id);
        departmentBeforeDelete.setActive(true);
        Department departmentAfterDelete = Department.builder().name("Department1").build();
        departmentAfterDelete.setId(id);
        departmentAfterDelete.setActive(false);

        when(departmentRepository.findByIdAndIsActiveTrue(id)).thenReturn(departmentBeforeDelete);
        doAnswer(invocation -> null).when(departmentRepository).save(any(Department.class));
        //Arrange
        CustomResponseDto response = departmentService.deleteDepartment(id);
        //Assert
        assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @Test
    public void deleteDepartment_WhenDepartmentNotFound_ReturnDepartmentNotFoundException() {
        //Act
        when(departmentRepository.findByIdAndIsActiveTrue(any(UUID.class))).thenReturn(null);
        //Arrange
        //Assert
        assertThrows(DepartmentNotFoundException.class, () -> departmentService.deleteDepartment(UUID.randomUUID()));
    }
}
