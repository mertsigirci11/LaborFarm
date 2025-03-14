package com.laborfarm.core_app.service.implementation;

import com.laborfarm.core_app.entity.User;
import com.laborfarm.core_app.repository.UserRepository;
import com.laborfarm.core_app.service.UserService;
import com.laborfarm.core_app.service.dto.CustomResponseDto;
import com.laborfarm.core_app.service.dto.UserDto;
import com.laborfarm.core_app.service.exception.user.UserAlreadyExistsException;
import com.laborfarm.core_app.service.exception.user.UserNotActiveException;
import com.laborfarm.core_app.service.exception.user.UserNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CustomResponseDto<UserDto> addUser(UserDto userDto) {
        if (userRepository.existsById(userDto.getId())) {
            throw new UserAlreadyExistsException();
        }
        //Convert to Entity
        User user = convertToEntity(userDto);

        //Saving
        user.setActive(true);
        User savedUser = userRepository.save(user);

        return CustomResponseDto.success(HttpStatus.CREATED.value(), convertToDto(savedUser));
    }

    @Override
    public CustomResponseDto<List<UserDto>> getAllUsers() {
        List<UserDto> userDtoList = userRepository.findByIsActiveTrue()
                .stream()
                .map(this::convertToDto)
                .toList();

        return CustomResponseDto.success(HttpStatus.OK.value(), userDtoList);
    }

    @Override
    public CustomResponseDto<UserDto> findUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);

        if (!user.isActive()){
            throw new UserNotActiveException();
        }

        return CustomResponseDto.success(HttpStatus.OK.value(), convertToDto(user));
    }

    @Override
    public CustomResponseDto<UserDto> updateUser(UserDto userDto) {
        User user = userRepository.findByIdAndIsActiveTrue(userDto.getId());

        if (user == null) {
            throw new UserNotFoundException();
        }

        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setUpdatedAt(new Date());
        user = userRepository.save(user);

        return CustomResponseDto.success(HttpStatus.OK.value(), convertToDto(user));
    }

    @Override
    public CustomResponseDto deleteUserById(UUID id) {
        User user = userRepository.findByIdAndIsActiveTrue(id);
        if (user == null) {
            throw new UserNotFoundException();
        }
        user.setUpdatedAt(new Date());
        user.setActive(false);
        userRepository.save(user);

        return CustomResponseDto.success(HttpStatus.OK.value());
    }

    //Helper Methods
    public UserDto convertToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    public User convertToEntity(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }
}
