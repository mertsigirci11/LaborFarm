package com.laborfarm.core_app.repository;

import com.laborfarm.core_app.entity.task.TaskComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TaskCommentRepository extends JpaRepository<TaskComment, UUID> {
    List<TaskComment> findByIsActiveTrue();
    TaskComment findByIdAndIsActiveTrue(UUID id);
    List<TaskComment> findByTaskIdAndIsActiveTrue(UUID taskId);
}