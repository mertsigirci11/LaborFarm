package com.laborfarm.core_app.entity.task;

import com.laborfarm.core_app.entity.BaseEntity;
import com.laborfarm.core_app.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "task_comments")
public class TaskComment extends BaseEntity {
    @Column(name = "comment")
    private String comment;

    @Column(name = "task_id")
    private UUID taskId;

    @Column(name = "creator_user_id")
    private UUID creatorUserId;

    @ManyToOne
    @JoinColumn(name = "task_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private Task task;

    @ManyToOne
    @JoinColumn(name = "creator_user_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private User creatorUser;
}
