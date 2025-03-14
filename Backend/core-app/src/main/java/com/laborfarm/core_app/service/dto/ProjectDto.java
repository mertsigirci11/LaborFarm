package com.laborfarm.core_app.service.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Data
@Getter
@Setter
public class ProjectDto {
    private UUID id;

    @NotNull(message = "Project name can't be null.")
    @Size(min = 2, max = 70, message = "Project name must contain between 2 and 70 characters.")
    private String name;

    @Size(max = 250, message = "Title must contain maximum 250 characters.")
    private String title;

    private String description;
    private UUID departmentId;
    private int statusId;
}
