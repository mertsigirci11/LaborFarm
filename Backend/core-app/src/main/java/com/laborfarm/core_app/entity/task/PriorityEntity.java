package com.laborfarm.core_app.entity.task;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "priorities")
public class PriorityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private Priority name;

    @OneToMany(mappedBy = "priority", cascade = CascadeType.ALL)
    private List<Task> tasks;
}