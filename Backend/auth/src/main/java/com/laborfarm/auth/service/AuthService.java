package com.laborfarm.auth.service;

import com.laborfarm.auth.entity.dto.CustomResponseDto;
import com.laborfarm.auth.entity.dto.login.LoginRequestDto;
import com.laborfarm.auth.entity.dto.login.LoginResponseDto;
import com.laborfarm.auth.entity.dto.register.RegisterRequestDto;
import com.laborfarm.auth.entity.dto.register.RegisterResponseDto;
import com.laborfarm.auth.entity.dto.role.RoleRequestDto;
import com.laborfarm.auth.entity.dto.role.RoleResponseDto;

import java.util.UUID;

public interface AuthService {
    CustomResponseDto<RegisterResponseDto> register(RegisterRequestDto registerRequestDto);
    CustomResponseDto<RegisterResponseDto> adminRegister(RegisterRequestDto registerRequestDto);
    CustomResponseDto<LoginResponseDto> login(LoginRequestDto loginRequestDto);
    CustomResponseDto<RoleResponseDto> createRole(RoleRequestDto roleRequestDto);
    CustomResponseDto deleteRole(UUID roleId);
    boolean verifyToken(String token);
}