package com.laborfarm.core_app.controller;

import com.laborfarm.core_app.service.UserService;
import com.laborfarm.core_app.service.dto.CustomResponseDto;
import com.laborfarm.common.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    private UUID sampleUserId;
    private UserDto userDto;
    private List<UserDto> userList;

    @BeforeEach
    void setUp() {
        sampleUserId = UUID.randomUUID();

        userDto = new UserDto();
        userDto.setId(sampleUserId);
        userDto.setFirstName("John");
        userDto.setLastName("Doe");
        userDto.setEmail("john.doe@example.com");

        userList = new ArrayList<>();
        userList.add(userDto);
    }

    @Test
    void testGetAllUsers() {
        CustomResponseDto<List<UserDto>> mockResponse = CustomResponseDto.success(200, userList);
        when(userService.getAllUsers()).thenReturn(mockResponse);

        ResponseEntity<CustomResponseDto<List<UserDto>>> response = userController.getAllUsers();

        assertEquals(200, response.getBody().getStatusCode());
        assertEquals(1, response.getBody().getData().size());
    }

    @Test
    void testCreateUser() {
        CustomResponseDto<UserDto> mockResponse = CustomResponseDto.success(201, userDto);
        when(userService.addUser(userDto)).thenReturn(mockResponse);

        ResponseEntity<CustomResponseDto<UserDto>> response = userController.createUser(userDto);

        assertEquals(201, response.getBody().getStatusCode());
        assertEquals(sampleUserId, response.getBody().getData().getId());
    }

    @Test
    void testGetUserById() {
        CustomResponseDto<UserDto> mockResponse = CustomResponseDto.success(200, userDto);
        when(userService.findUserById(sampleUserId)).thenReturn(mockResponse);

        ResponseEntity<CustomResponseDto<UserDto>> response = userController.getUserById(sampleUserId);

        assertEquals(200, response.getBody().getStatusCode());
        assertEquals(sampleUserId, response.getBody().getData().getId());
    }

    @Test
    void testUpdateUser() {
        CustomResponseDto<UserDto> mockResponse = CustomResponseDto.success(200, userDto);
        when(userService.updateUser(userDto)).thenReturn(mockResponse);

        ResponseEntity<CustomResponseDto<UserDto>> response = userController.updateUser(userDto);

        assertEquals(200, response.getBody().getStatusCode());
        assertEquals(sampleUserId, response.getBody().getData().getId());
    }

    @Test
    void testDeleteUser() {
        CustomResponseDto<Void> mockResponse = CustomResponseDto.success(204);
        when(userService.deleteUserById(sampleUserId)).thenReturn(mockResponse);

        ResponseEntity<CustomResponseDto> response = userController.deleteUser(sampleUserId);

        assertEquals(204, response.getBody().getStatusCode());
    }
}