package com.laborfarm.core_app.repository;

import com.laborfarm.core_app.entity.project.ProjectMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProjectMemberRepository extends JpaRepository<ProjectMember, UUID> {
    List<ProjectMember> findByIsActiveTrue();
    ProjectMember findByIdAndIsActiveTrue(UUID id);
    List<ProjectMember> getProjectMembersByProjectId(UUID projectId);

    @Query("SELECT COUNT(p) > 0 FROM Project p WHERE p.id = :projectId AND p.isActive = true")
    boolean isProjectExists(@Param("projectId") UUID projectId);

    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.id = :userId AND u.isActive = true")
    boolean isUserExists(@Param("userId") UUID userId);

    @Query("SELECT COUNT(pm) > 0 FROM ProjectMember pm WHERE pm.project.id = :projectId AND pm.user.id = :userId AND pm.isActive = true")
    boolean isUserExistsInProject(@Param("projectId") UUID projectId, @Param("userId") UUID userId);

    @Query("SELECT pm FROM ProjectMember pm WHERE pm.userId = :userId AND pm.isActive = true")
    List<ProjectMember> getMembershipInfosByUserId(@Param("userId") UUID userId);


}