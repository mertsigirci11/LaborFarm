package com.laborfarm.core_app.entity.task;

import com.laborfarm.core_app.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "files")
public class FileInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "file_name", length = 70)
    private String fileName;

    @Column(name = "extension")
    private String extension;

    @Column(name = "content_type")
    private String contentType;

    @Column(name = "location")
    private String location;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "task_id")
    private UUID taskId;

    @Column(name = "is_active")
    private Boolean isActive;


    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "task_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private Task task;
}