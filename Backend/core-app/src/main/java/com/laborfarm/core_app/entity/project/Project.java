package com.laborfarm.core_app.entity.project;

import com.laborfarm.core_app.entity.BaseEntity;
import com.laborfarm.core_app.entity.Department;
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
@Table(name = "projects")
public class Project extends BaseEntity {

    @Column(name = "project_name", length = 70)
    @NotNull(message = "Project name can't be null.")
    @Size(min = 2, max = 70, message = "Project name must contain between 2 and 70 characters.")
    private String name;

    @Column(name = "title", length = 250)
    @Size(max = 250, message = "Title must contain maximum 250 characters.")
    private String title;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "department_id", referencedColumnName = "id", nullable = false)
    private Department department;

    @ManyToOne
    @JoinColumn(name = "status_id", referencedColumnName = "id", nullable = false)
    private StatusEntity status;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProjectMember> projectMembers;
}
