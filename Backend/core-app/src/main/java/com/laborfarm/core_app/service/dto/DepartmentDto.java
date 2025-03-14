package com.laborfarm.core_app.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;

import java.util.Date;
import java.util.UUID;

@Data
@Getter
public class DepartmentDto {
    private UUID id;

    @NotNull(message = "Department name can't be null.")
    @Size(min = 2, max = 70, message = "Department name must contain between 2 and 70 characters.")
    private String name;

    private Date createdAt;
    private Date updatedAt;
}
