package com.laborfarm.core_app.entity.task;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "states")
public class StateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false, length = 50)
    private State name;

    @OneToMany(mappedBy = "state", cascade = CascadeType.ALL)
    private List<Task> tasks;
}
