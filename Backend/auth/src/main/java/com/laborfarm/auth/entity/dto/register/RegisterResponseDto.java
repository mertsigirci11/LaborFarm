package com.laborfarm.auth.entity.dto.register;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class RegisterResponseDto {
    private UUID id;
    private String email;
    private String firstName;
    private String lastName;
}