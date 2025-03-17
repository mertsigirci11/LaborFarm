package com.laborfarm.core_app.entity.task;

import com.laborfarm.core_app.entity.BaseEntity;
import com.laborfarm.core_app.entity.User;
import com.laborfarm.core_app.entity.project.Project;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tasks")
public class Task extends BaseEntity {

    @Column(name = "user_story_desc")
    private String userStoryDescription;

    @Column(name = "acceptance_criteria")
    private String acceptanceCriteria;

    @Column(name = "cancelled_blocked_reason")
    private String cancelledOrBlockedReason;

    @Column(name = "assignee_id")
    private UUID assigneeId;

    @Column(name = "project_id")
    private UUID projectId;

    @Column(name = "state_id")
    private int stateId;

    @Column(name = "priority_id")
    private int priorityId;

    @ManyToOne
    @JoinColumn(name = "state_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private StateEntity state;

    @ManyToOne
    @JoinColumn(name = "priority_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private PriorityEntity priority;

    @ManyToOne
    @JoinColumn(name = "assignee_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User assignee;

    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Project project;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    private List<TaskComment> taskComments;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FileInfo> attachedFiles;
}
