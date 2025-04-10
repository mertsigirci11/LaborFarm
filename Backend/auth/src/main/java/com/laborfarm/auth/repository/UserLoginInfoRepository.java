package com.laborfarm.auth.repository;

import com.laborfarm.auth.entity.UserLoginInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserLoginInfoRepository extends JpaRepository<UserLoginInfo, UUID> {
    Optional<UserLoginInfo> findByEmail(String email);
}