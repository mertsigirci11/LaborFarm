package com.laborfarm.core_app.repository;

import com.laborfarm.core_app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    List<User> findByIsActiveTrue();
    User findByIdAndIsActiveTrue(UUID id);
}
