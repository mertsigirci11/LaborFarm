package com.laborfarm.core_app.entity.project;

import com.laborfarm.core_app.entity.BaseEntity;
import com.laborfarm.core_app.entity.Department;
import com.laborfarm.core_app.entity.task.Task;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "projects")
public class Project extends BaseEntity {

    @Column(name = "project_name", length = 70)
    private String name;

    @Column(name = "title", length = 250)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "department_id", insertable = false, updatable = false)
    private UUID departmentId;

    @Column(name = "status_id", insertable = false, updatable = false)
    private int statusId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", referencedColumnName = "id", nullable = false)
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id", referencedColumnName = "id", nullable = false)
    private StatusEntity status;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProjectMember> projectMembers;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks;
}
