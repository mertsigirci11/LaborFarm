package com.laborfarm.domain.task;

import com.laborfarm.domain.BaseEntity;
import com.laborfarm.domain.User;
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
@Table(name = "task")
public class Task extends BaseEntity {

    @Column(name = "user_story_desc")
    private String userStoryDescription;

    @Column(name = "acceptance_criteria")
    private String acceptanceCriteria;

    @Column(name = "cancelled_blocked_reason")
    private String cancelledOrBlockedReason;

    @ManyToOne
    @JoinColumn(name = "state_id", referencedColumnName = "id", nullable = false)
    private StateEntity state;

    @ManyToOne
    @JoinColumn(name = "priority_id", referencedColumnName = "id", nullable = false)
    private PriorityEntity priority;

    @ManyToOne
    @JoinColumn(name = "assignee_id", referencedColumnName = "id", nullable = false)
    private User assignee;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TaskComment> comments;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FileInfo> attachedFiles;
}
