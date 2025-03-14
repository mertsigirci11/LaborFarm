package com.laborfarm.core_app.service.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Data
@Getter
@Setter
public class UserDto {
    private UUID id;

    @NotNull(message = "First name can't be null.")
    @Size(min = 2, max = 50, message = "First name must contain between 2 and 70 characters.")
    private String firstName;

    @NotNull(message = "Last name can't be null.")
    @Size(min = 2, max = 50, message = "Last name must contain between 2 and 70 characters.")
    private String lastName;

    private String email;
    private Date createdAt;
    private Date updatedAt;
}
