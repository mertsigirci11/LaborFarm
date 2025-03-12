package com.laborfarm.core_app.entity;

import com.laborfarm.core_app.entity.project.Project;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "departments")
public class Department extends BaseEntity {

    @Column(name = "department_name", length = 70)
    @NotNull(message = "Department name can't be null.")
    @Size(min = 2, max = 70, message = "Department name must contain between 2 and 70 characters.")
    private String name;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Project> projects;
}
