package com.laborfarm.core_app.repository;

import com.laborfarm.core_app.entity.project.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProjectRepository extends JpaRepository<Project, UUID> {
    List<Project> findByIsActiveTrue();
    Project findByIdAndIsActiveTrue(UUID id);
}
