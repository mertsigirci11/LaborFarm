package com.laborfarm.core_app.entity.task;

import com.laborfarm.core_app.entity.BaseEntity;
import com.laborfarm.core_app.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "task_comments")
public class TaskComment extends BaseEntity {
    @Column(name = "comment")
    @NotNull(message = "Comment can't be null.")
    private String comment;

    @ManyToOne
    @JoinColumn(name = "task_id", referencedColumnName = "id", nullable = false)
    private Task task;

    @ManyToOne
    @JoinColumn(name = "creator_user_id", referencedColumnName = "id", nullable = false)
    private User creatorUser;
}
