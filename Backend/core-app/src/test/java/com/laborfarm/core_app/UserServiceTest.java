package com.laborfarm.core_app;

import com.laborfarm.common.UserDto;
import com.laborfarm.core_app.entity.User;
import com.laborfarm.core_app.repository.UserRepository;
import com.laborfarm.core_app.service.UserService;
import com.laborfarm.core_app.service.dto.CustomResponseDto;
import com.laborfarm.core_app.service.exception.user.UserAlreadyExistsException;
import com.laborfarm.core_app.service.exception.user.UserNotActiveException;
import com.laborfarm.core_app.service.exception.user.UserNotFoundException;
import com.laborfarm.core_app.service.implementation.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private ModelMapper modelMapper = new ModelMapper();

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userRepository, modelMapper);
    }

    @Test
    public void addUser_ReturnsUser() {
        //Arrange
        UUID uuid = UUID.randomUUID();
        UserDto userDto = new UserDto();
        userDto.setFirstName("John");
        userDto.setLastName("Doe");
        userDto.setEmail("john.doe@example.com");
        User user = User.builder()
                .id(uuid)
                .email("john.doe@example.com")
                .firstName("John")
                .lastName("Doe")
                .isActive(true)
                .build();
        User userToBeAdded = modelMapper.map(userDto, User.class);
        when(userRepository.save(userToBeAdded)).thenReturn(user);

        //Act
        CustomResponseDto<UserDto> response = userService.addUser(userDto);

        //Assert
        assertThat(response.getData().getId()).isEqualTo(uuid);
    }

    @Test
    public void addUser_WhenIdIsExists_ThrowsUserAlreadyExistsException() {
        //Arrange
        UUID uuid = UUID.randomUUID();
        UserDto userDto = new UserDto();
        userDto.setId(uuid);
        when(userRepository.existsById(any(UUID.class))).thenReturn(true);
        //Act
        //Assert
        assertThrows(UserAlreadyExistsException.class, () -> userService.addUser(userDto));
    }

    @Test
    public void getAllUsers_ReturnsAllUsers() {
        //Arrange
        UUID uuid = UUID.randomUUID();
        User user1 = new User();
        user1.setId(uuid);
        user1.setFirstName("John");
        user1.setLastName("Doe");
        user1.setEmail("john.doe@example.com");
        List<User> users = List.of(user1);

        UserDto userDto1 = new UserDto();
        userDto1.setId(uuid);
        userDto1.setFirstName("John");
        userDto1.setLastName("Doe");
        userDto1.setEmail("john.doe@example.com");
        List<UserDto> userDtos = List.of(userDto1);

        when(userRepository.findByIsActiveTrue()).thenReturn(users);

        //Act
        CustomResponseDto<List<UserDto>> response = userService.getAllUsers();

        //Assert
        assertThat(response.getData().get(0).getId()).isEqualTo(userDtos.get(0).getId());
        assertThat(response.getData().get(0).getFirstName()).isEqualTo(userDtos.get(0).getFirstName());
        assertThat(response.getData().get(0).getLastName()).isEqualTo(userDtos.get(0).getLastName());
        assertThat(response.getData().get(0).getEmail()).isEqualTo(userDtos.get(0).getEmail());
    }

    @Test
    public void findUserById_ReturnsUserDto() {
        //Arrange
        UUID uuid = UUID.randomUUID();
        User user = new User();
        user.setId(uuid);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        UserDto userDto = modelMapper.map(user, UserDto.class);

        when(userRepository.findById(uuid)).thenReturn(Optional.of(user));
        //Act
        CustomResponseDto<UserDto> response = userService.findUserById(uuid);

        //Assert
        assertThat(response.getData()).isEqualTo(userDto);
    }

    @Test
    public void findUserById_WhenIdIsNotExists_ThrowsUserNotFoundException() {
        //Arrange
        when(userRepository.findById(any(UUID.class))).thenReturn(Optional.empty());
        //Act
        //Assert
        assertThrows(UserNotFoundException.class, () -> userService.findUserById(UUID.randomUUID()));
    }

    @Test
    public void findUserById_WhenUserIsNotActive_ThrowsUserNotActiveException() {
        //Arrange
        User user = new User();
        user.setActive(false);
        when(userRepository.findById(any(UUID.class))).thenReturn(Optional.of(user));
        //Act
        //Assert
        assertThrows(UserNotActiveException.class, () -> userService.findUserById(UUID.randomUUID()));
    }

    @Test
    public void updateUser_ReturnsUpdatedUser() {
        //Arrange
        UUID uuid = UUID.randomUUID();
        User existingUser = new User();
        existingUser.setId(uuid);
        existingUser.setFirstName("John");
        existingUser.setLastName("Doe");
        existingUser.setEmail("john.doe@example.com");

        UserDto userDto = new UserDto();
        userDto.setId(uuid);
        userDto.setFirstName("Matt");
        userDto.setLastName("Shepherd");
        userDto.setEmail("matt.shepherd@example.com");

        User updatedUser = modelMapper.map(userDto, User.class);
        when(userRepository.findByIdAndIsActiveTrue(userDto.getId())).thenReturn(existingUser);
        when(userRepository.save(existingUser)).thenReturn(updatedUser);

        //Act
        CustomResponseDto<UserDto> response = userService.updateUser(userDto);

        //Assert
        assertThat(response.getData().getId()).isEqualTo(updatedUser.getId());
        assertThat(response.getData().getFirstName()).isEqualTo(updatedUser.getFirstName());
        assertThat(response.getData().getLastName()).isEqualTo(updatedUser.getLastName());
        assertThat(response.getData().getEmail()).isEqualTo(updatedUser.getEmail());
    }

    @Test
    public void updateUser_WhenUserNotFound_ThrowsUserNotFoundException() {
        //Arrange
        UserDto userDto = new UserDto();
        userDto.setId(UUID.randomUUID());
        when(userRepository.findByIdAndIsActiveTrue(any(UUID.class))).thenReturn(null);
        //Act
        //Assert
        assertThrows(UserNotFoundException.class, () -> userService.updateUser(userDto));
    }

    @Test
    public void deleteUserById_ReturnSuccessResponse() {
        //Arrange
        UUID uuid = UUID.randomUUID();
        User user = new User();
        user.setActive(true);
        user.setId(uuid);

        when(userRepository.findByIdAndIsActiveTrue(uuid)).thenReturn(user);
        //Act
        CustomResponseDto response = userService.deleteUserById(uuid);

        //Assert
        assertThat(user.isActive()).isFalse();
        assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @Test
    public void deleteUserById_WhenUserNotFound_ThrowsUserNotFoundException() {
        //Arrange
        when(userRepository.findByIdAndIsActiveTrue(any(UUID.class))).thenReturn(null);
        //Act
        //Assert
        assertThrows(UserNotFoundException.class, () -> userService.deleteUserById(UUID.randomUUID()));
    }
}
