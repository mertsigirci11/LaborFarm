package com.laborfarm.auth.repository;

import com.laborfarm.auth.entity.UserRoleInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRoleInfoRepository extends JpaRepository<UserRoleInfo, UUID> {
    List<UserRoleInfo> findByIsActiveTrue();
    List<UserRoleInfo> findByUserIdAndIsActiveTrue(UUID userId);
    UserRoleInfo findByProjectId(UUID projectId);
    UserRoleInfo findByIdAndIsActiveTrue(UUID roleId);
}
