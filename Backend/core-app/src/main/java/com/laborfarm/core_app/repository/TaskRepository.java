package com.laborfarm.core_app.repository;

import com.laborfarm.core_app.entity.task.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {
    List<Task> findByIsActiveTrue();
    Task findByIdAndIsActiveTrue(UUID id);

    @Query("SELECT t FROM Task t WHERE t.isActive = true AND t.stateId = :stateId")
    List<Task> getTasksByStateId(@Param("stateId") int stateId);

    @Query("SELECT t FROM Task t WHERE t.isActive = true AND t.priorityId = :priorityId")
    List<Task> getTasksByPriorityId(@Param("priorityId") int priorityId);

    @Query("SELECT t FROM Task t WHERE t.isActive = true AND t.projectId = :projectId")
    List<Task> getTasksByProjectId(@Param("projectId")UUID projectId);

    @Query("SELECT t FROM Task t WHERE t.isActive = true AND t.assigneeId = :userId")
    List<Task> getTasksByUserId(@Param("userId")UUID userId);

    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.isActive = true AND u.id = :userId")
    boolean isAssigneeExist(@Param("userId")UUID userId);

    @Query("SELECT COUNT(p) > 0 FROM Project p WHERE p.isActive = true AND p.id = :projectId")
    boolean isProjectExist(@Param("projectId")UUID projectId);

}
