package com.laborfarm.auth.service.implementation;

import com.laborfarm.auth.entity.UserLoginInfo;
import com.laborfarm.auth.entity.UserRole;
import com.laborfarm.auth.entity.UserRoleInfo;
import com.laborfarm.auth.entity.dto.CustomResponseDto;
import com.laborfarm.auth.entity.dto.login.LoginRequestDto;
import com.laborfarm.auth.entity.dto.login.LoginResponseDto;
import com.laborfarm.auth.entity.dto.register.RegisterRequestDto;
import com.laborfarm.auth.entity.dto.register.RegisterResponseDto;
import com.laborfarm.auth.entity.dto.role.RoleRequestDto;
import com.laborfarm.auth.entity.dto.role.RoleResponseDto;
import com.laborfarm.auth.exception.EmailAlreadyExistsException;
import com.laborfarm.auth.exception.EmailNotFoundException;
import com.laborfarm.auth.exception.RoleNotFoundException;
import com.laborfarm.auth.exception.UserNotFoundException;
import com.laborfarm.auth.repository.UserLoginInfoRepository;
import com.laborfarm.auth.repository.UserRoleInfoRepository;
import com.laborfarm.auth.service.AuthService;
import com.laborfarm.auth.service.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserLoginInfoRepository loginInfoRepository;
    private final UserRoleInfoRepository roleInfoRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager, UserLoginInfoRepository loginInfoRepository,
                           UserRoleInfoRepository roleInfoRepository, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.loginInfoRepository = loginInfoRepository;
        this.roleInfoRepository = roleInfoRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public CustomResponseDto<RegisterResponseDto> register(RegisterRequestDto registerRequestDto) {
        RegisterResponseDto response = saveUserLoginInfo(registerRequestDto);

        //Send post request to monolith app user endpoint requestBody-> registerResponseDto
        //Will be completed

        return CustomResponseDto.success(HttpStatus.CREATED.value(), response);
    }

    @Override
    public CustomResponseDto<RegisterResponseDto> adminRegister(RegisterRequestDto registerRequestDto) {
        RegisterResponseDto response = saveUserLoginInfo(registerRequestDto);

        //Giving super user(PROJECT GROUP MANAGER) authority
        UserRoleInfo userRoleInfo = UserRoleInfo.builder()
                .userId(response.getId())
                .roleId(UserRole.PROJECT_GROUP_MANAGER.getValue())
                .isActive(true)
                .build();

        roleInfoRepository.save(userRoleInfo);

        //Send post request to monolith app user endpoint requestBody-> registerResponseDto
        //Will be completed

        return CustomResponseDto.success(HttpStatus.CREATED.value(), response);
    }

    @Override
    public CustomResponseDto<LoginResponseDto> login(LoginRequestDto loginRequestDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.getEmail(),
                        loginRequestDto.getPassword()
                )
        );

        UserLoginInfo user = loginInfoRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new EmailNotFoundException());
        List<UserRoleInfo> userRoleList = roleInfoRepository.findByUserIdAndIsActiveTrue(user.getId());

        LoginResponseDto response = new LoginResponseDto();
        String jwt = jwtUtil.generateToken(user, userRoleList);
        response.setToken(jwt);

        return CustomResponseDto.success(HttpStatus.OK.value(), response);
    }

    @Override
    public CustomResponseDto<RoleResponseDto> createRole(RoleRequestDto roleRequestDto) {
        //Check user existance
        UserLoginInfo userToBeAuthorized = loginInfoRepository.findById(roleRequestDto.getUserId())
                .orElseThrow(() -> new UserNotFoundException());

        //Save the role
        UserRoleInfo roleToBeSavedOrUpdated = roleInfoRepository.findByProjectId(roleRequestDto.getProjectId());

        roleToBeSavedOrUpdated.setProjectId(roleRequestDto.getProjectId());
        roleToBeSavedOrUpdated.setUserId(roleRequestDto.getId());
        roleToBeSavedOrUpdated.setActive(true);

        UserRoleInfo savedRole = roleInfoRepository.save(roleToBeSavedOrUpdated);
        RoleResponseDto response = roleResponseMapper(savedRole);

        return CustomResponseDto.success(HttpStatus.CREATED.value(), response);
    }

    @Override
    public CustomResponseDto deleteRole(UUID roleId) {
        //Check role existance
        UserRoleInfo roleToBeDeleted = roleInfoRepository.findByIdAndIsActiveTrue(roleId);
        if (roleToBeDeleted == null) {
            throw new RoleNotFoundException();
        }

        roleToBeDeleted.setActive(false);
        roleInfoRepository.save(roleToBeDeleted);
        return CustomResponseDto.success(HttpStatus.NO_CONTENT.value());
    }

    //Helper
    private UserLoginInfo userMapper(RegisterRequestDto registerRequestDto) {
        Optional<UserLoginInfo> userToBeChecked = loginInfoRepository.findByEmail(registerRequestDto.getEmail());

        if (userToBeChecked.isPresent()) {
            throw new EmailAlreadyExistsException();
        }

        UserLoginInfo userLoginInfo = UserLoginInfo.builder()
                .email(registerRequestDto.getEmail())
                .password(passwordEncoder.encode(registerRequestDto.getPassword()))
                .build();

        return userLoginInfo;
    }
    private RegisterResponseDto registerResponseMapper(RegisterRequestDto registerRequestDto, UserLoginInfo savedUser){
        RegisterResponseDto registerResponseDto = RegisterResponseDto.builder()
                .id(savedUser.getId())
                .email(savedUser.getEmail())
                .firstName(registerRequestDto.getFirstName())
                .lastName(registerRequestDto.getLastName())
                .build();
        return registerResponseDto;
    }
    private RoleResponseDto roleResponseMapper(UserRoleInfo savedRole){
        RoleResponseDto response = RoleResponseDto.builder()
                .id(savedRole.getId())
                .projectId(savedRole.getProjectId())
                .userId(savedRole.getUserId())
                .roleId(savedRole.getRoleId())
                .roleName(UserRole.fromValue(savedRole.getRoleId()).name())
                .build();

        return response;
    }
    private RegisterResponseDto saveUserLoginInfo(RegisterRequestDto registerRequestDto){
        UserLoginInfo userLoginInfo = userMapper(registerRequestDto);
        UserLoginInfo savedUser = loginInfoRepository.save(userLoginInfo);
        return registerResponseMapper(registerRequestDto, savedUser);
    }
}