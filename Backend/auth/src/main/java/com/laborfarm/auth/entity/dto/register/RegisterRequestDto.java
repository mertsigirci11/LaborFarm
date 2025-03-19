package com.laborfarm.auth.entity.dto.register;

import lombok.Data;

@Data
public class RegisterRequestDto {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
}