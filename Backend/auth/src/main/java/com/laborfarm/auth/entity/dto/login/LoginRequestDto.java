package com.laborfarm.auth.entity.dto.login;

import lombok.Data;

@Data
public class LoginRequestDto {
    private String email;
    private String password;
}