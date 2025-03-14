package com.laborfarm.core_app.controller;

import com.laborfarm.core_app.service.UserService;
import com.laborfarm.core_app.service.dto.CustomResponseDto;
import com.laborfarm.core_app.service.dto.UserDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/v1")
    public ResponseEntity<CustomResponseDto<List<UserDto>>> getAllUsers() {
        CustomResponseDto<List<UserDto>> response = userService.getAllUsers();
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @PostMapping("/v1")
    public ResponseEntity<CustomResponseDto<UserDto>> createUser(@RequestBody @Valid UserDto user) {
        CustomResponseDto<UserDto> response = userService.addUser(user);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @GetMapping("/v1/{id}")
    public ResponseEntity<CustomResponseDto<UserDto>> getUserById(@PathVariable UUID id) {
        CustomResponseDto<UserDto> response = userService.findUserById(id);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @PutMapping("/v1")
    public ResponseEntity<CustomResponseDto<UserDto>> updateUser(@RequestBody @Valid UserDto user) {
        CustomResponseDto<UserDto> response = userService.updateUser(user);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @DeleteMapping("/v1/{id}")
    public ResponseEntity<CustomResponseDto> deleteUser(@PathVariable UUID id) {
        CustomResponseDto response = userService.deleteUserById(id);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }
}
