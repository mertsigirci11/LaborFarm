package com.laborfarm.core_app.entity.task;

import com.laborfarm.core_app.entity.User;
import jakarta.persistence.*;
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
    @NotNull(message = "File name can't be null.")
    @Size(max = 70, message = "File name contains maximum 70 characters.")
    private String fileName;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "task_id", referencedColumnName = "id", nullable = false)
    private Task task;
}
