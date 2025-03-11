package com.laborfarm.domain.auth;

import com.laborfarm.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "user_role")
public class UserRole extends BaseEntity {

    @Column(name = "project_id")
    private UUID projectId;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "role", length = 50)
    private RoleType role;
}