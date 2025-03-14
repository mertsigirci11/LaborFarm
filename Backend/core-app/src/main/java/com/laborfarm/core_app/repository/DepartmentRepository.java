package com.laborfarm.core_app.repository;

import com.laborfarm.core_app.entity.Department;
import com.laborfarm.core_app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, UUID> {
    List<Department> findByIsActiveTrue();
    Department findByIdAndIsActiveTrue(UUID id);
}