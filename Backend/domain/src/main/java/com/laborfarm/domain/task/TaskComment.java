package com.laborfarm.domain.task;

import com.laborfarm.domain.BaseEntity;
import com.laborfarm.domain.User;
import jakarta.persistence.*;
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
@Table(name = "task_comment")
public class TaskComment extends BaseEntity {

    @Column(name = "comment")
    private String comment;

    @ManyToOne
    @JoinColumn(name = "task_id", referencedColumnName = "id", nullable = false)
    private Task task;

    @ManyToOne
    @JoinColumn(name = "creator_user_id", referencedColumnName = "id", nullable = false)
    private User creatorUser;
}
