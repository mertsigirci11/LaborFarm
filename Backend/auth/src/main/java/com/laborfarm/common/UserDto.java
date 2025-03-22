package com.laborfarm.common;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
public class UserDto {
    private UUID id;

    private String firstName;
    private String lastName;

    private String email;
    private Date createdAt;
    private Date updatedAt;
}
