package com.laborfarm.core_app.service.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Data
@Getter
@Setter
public class ProjectResponseDto {
    private UUID id;
    private String name;
    private String description;
    private String title;
    private String departmentName;
    private UUID departmentId;
    private String statusName;
    private int statusId;
    private Date createdAt;
    private Date updatedAt;
}
