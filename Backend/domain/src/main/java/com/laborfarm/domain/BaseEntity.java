package com.laborfarm.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "creating_date")
    private Date createdAt;

    @Column(name = "updating_date")
    private Date updatedAt;

    @Column(name = "is_active")
    private boolean isActive = true;
}
