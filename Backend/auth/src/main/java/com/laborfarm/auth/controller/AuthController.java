package com.laborfarm.auth.controller;

import com.laborfarm.auth.entity.dto.CustomResponseDto;
import com.laborfarm.auth.entity.dto.login.LoginRequestDto;
import com.laborfarm.auth.entity.dto.login.LoginResponseDto;
import com.laborfarm.auth.entity.dto.register.RegisterRequestDto;
import com.laborfarm.auth.entity.dto.register.RegisterResponseDto;
import com.laborfarm.auth.entity.dto.role.RoleRequestDto;
import com.laborfarm.auth.entity.dto.role.RoleResponseDto;
import com.laborfarm.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<CustomResponseDto<RegisterResponseDto>> register(@RequestBody RegisterRequestDto registerRequestDto) {
        CustomResponseDto<RegisterResponseDto> response = authService.register(registerRequestDto);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @PostMapping("/register/admin")
    public ResponseEntity<CustomResponseDto<RegisterResponseDto>> adminRegister(@RequestBody RegisterRequestDto registerRequestDto) {
        CustomResponseDto<RegisterResponseDto> response = authService.adminRegister(registerRequestDto);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @GetMapping("/login")
    public ResponseEntity<CustomResponseDto<LoginResponseDto>> login(@RequestBody LoginRequestDto loginRequestDto) {
        CustomResponseDto<LoginResponseDto> response = authService.login(loginRequestDto);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @PostMapping("/role")
    public ResponseEntity<CustomResponseDto<RoleResponseDto>> createRole(@RequestBody RoleRequestDto roleRequestDto) {
        CustomResponseDto<RoleResponseDto> response = authService.createRole(roleRequestDto);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @DeleteMapping("/role/{roleId}")
    public ResponseEntity<CustomResponseDto> deleteRole(@PathVariable UUID roleId) {
        CustomResponseDto response = authService.deleteRole(roleId);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }
}