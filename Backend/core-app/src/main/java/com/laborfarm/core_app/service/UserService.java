package com.laborfarm.core_app.service;

import com.laborfarm.core_app.service.dto.CustomResponseDto;
import com.laborfarm.core_app.service.dto.UserDto;

import java.util.List;
import java.util.UUID;

public interface UserService {
    CustomResponseDto<UserDto> addUser(UserDto userDto);
    CustomResponseDto<List<UserDto>> getAllUsers();
    CustomResponseDto<UserDto> findUserById(UUID id);
    CustomResponseDto<UserDto> updateUser(UserDto userDto);
    CustomResponseDto deleteUserById(UUID id);
}
