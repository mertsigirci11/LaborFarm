package com.laborfarm.core_app.entity;

import com.laborfarm.core_app.entity.project.ProjectMember;
import com.laborfarm.core_app.entity.task.FileInfo;
import com.laborfarm.core_app.entity.task.Task;
import com.laborfarm.core_app.entity.task.TaskComment;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Column(name = "first_name", nullable = false, length = 50)
    @NotNull(message = "First name can't be null.")
    @Size(min = 2, max = 50, message = "First name must contain between 2 and 70 characters.")
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    @NotNull(message = "Last name can't be null.")
    @Size(min = 2, max = 50, message = "Last name must contain between 2 and 70 characters.")
    private String lastName;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProjectMember> projectMembers;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FileInfo> fileInfos;

    @OneToMany(mappedBy = "creatorUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TaskComment> taskComments;

    @OneToMany(mappedBy = "assignee", cascade = CascadeType.ALL)
    private List<Task> tasks;
}
