package com.laborfarm.core_app.entity.project;

import com.laborfarm.core_app.entity.BaseEntity;
import com.laborfarm.core_app.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "project_members")
public class ProjectMember extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "id", nullable = false)
    private Project project;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;
}
