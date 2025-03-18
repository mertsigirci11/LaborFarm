package com.laborfarm.auth.service.implementation;

import com.laborfarm.auth.entity.dto.CustomResponseDto;
import com.laborfarm.auth.entity.dto.login.LoginRequestDto;
import com.laborfarm.auth.entity.dto.login.LoginResponseDto;
import com.laborfarm.auth.entity.dto.register.RegisterRequestDto;
import com.laborfarm.auth.entity.dto.register.RegisterResponseDto;
import com.laborfarm.auth.entity.dto.role.RoleRequestDto;
import com.laborfarm.auth.entity.dto.role.RoleResponseDto;
import com.laborfarm.auth.repository.UserLoginInfoRepository;
import com.laborfarm.auth.repository.UserRoleInfoRepository;
import com.laborfarm.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserLoginInfoRepository userLoginInfoRepository;
    private final UserRoleInfoRepository roleInfoRepository;

    @Autowired
    public AuthServiceImpl(UserLoginInfoRepository userLoginInfoRepository, UserRoleInfoRepository roleInfoRepository) {
        this.userLoginInfoRepository = userLoginInfoRepository;
        this.roleInfoRepository = roleInfoRepository;
    }

    @Override
    public CustomResponseDto<RegisterResponseDto> register(RegisterRequestDto registerRequestDto) {
        return null;
    }

    @Override
    public CustomResponseDto<RegisterResponseDto> adminRegister(RegisterRequestDto registerRequestDto) {
        return null;
    }

    @Override
    public CustomResponseDto<LoginResponseDto> login(LoginRequestDto loginRequestDto) {
        return null;
    }

    @Override
    public CustomResponseDto<RoleResponseDto> createRole(RoleRequestDto roleRequestDto) {
        return null;
    }

    @Override
    public CustomResponseDto deleteRole(RoleRequestDto roleRequestDto) {
        return null;
    }
}
