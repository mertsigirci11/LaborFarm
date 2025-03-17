package com.laborfarm.core_app.repository;

import com.laborfarm.core_app.entity.task.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FileInfoRepository extends JpaRepository<FileInfo, UUID> {
    List<FileInfo> findByIsActiveTrue();
    FileInfo findByIdAndIsActiveTrue(UUID id);
    List<FileInfo> findByTaskIdAndIsActiveTrue(UUID taskId);
}