package com.laborfarm.core_app.entity.project;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "status")
public class StatusEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private Status name;

    @OneToMany(mappedBy = "status", cascade = CascadeType.ALL)
    private List<Project> project;
}
