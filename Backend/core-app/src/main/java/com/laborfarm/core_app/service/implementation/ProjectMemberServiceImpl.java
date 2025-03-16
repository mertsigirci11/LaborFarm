package com.laborfarm.core_app.service.implementation;

import com.laborfarm.core_app.entity.project.Project;
import com.laborfarm.core_app.entity.project.ProjectMember;
import com.laborfarm.core_app.repository.ProjectMemberRepository;
import com.laborfarm.core_app.service.ProjectMemberService;
import com.laborfarm.core_app.service.dto.*;
import com.laborfarm.core_app.service.exception.project.ProjectMemberNotFound;
import com.laborfarm.core_app.service.exception.project.ProjectNotFoundException;
import com.laborfarm.core_app.service.exception.project.UserAlreadyExistsInProject;
import com.laborfarm.core_app.service.exception.user.UserNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ProjectMemberServiceImpl implements ProjectMemberService {

    private final ProjectMemberRepository projectMemberRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ProjectMemberServiceImpl(ProjectMemberRepository projectMemberRepository, ModelMapper modelMapper) {
        this.projectMemberRepository = projectMemberRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CustomResponseDto<ProjectMemberResponseDto> addProjectMember(ProjectMemberRequestDto projectMemberRequestDto) {
        //Check if member exists
        boolean isUserExists = projectMemberRepository.isUserExists(projectMemberRequestDto.getUserId());
        if (!isUserExists) {
            throw new UserNotFoundException();
        }

        //Check if project exists
        boolean isProjectExists = projectMemberRepository.isProjectExists(projectMemberRequestDto.getProjectId());
        if (!isProjectExists) {
            throw new ProjectNotFoundException();
        }

        //Check if member exists in project
        boolean isUserExistsInProject = projectMemberRepository.isUserExistsInProject(projectMemberRequestDto.getProjectId(),
                projectMemberRequestDto.getUserId());
        if (isUserExistsInProject) {
            throw new UserAlreadyExistsInProject();
        }

        //Convert to entity
        ProjectMember projectMember = modelMapper.map(projectMemberRequestDto, ProjectMember.class);

        //Saving
        projectMember.setActive(true);
        projectMember.setCreatedAt(new Date());
        ProjectMember savedProjectMember = projectMemberRepository.save(projectMember);
        ProjectMemberResponseDto response = convertToDto(savedProjectMember);

        return CustomResponseDto.success(HttpStatus.CREATED.value(), response);
    }

    @Override
    public CustomResponseDto<List<ProjectMemberResponseDto>> getAllProjectMembers() {
        List<ProjectMemberResponseDto> projectMemberResponseDtoList = projectMemberRepository.findByIsActiveTrue()
                .stream()
                .map(this::convertToDto)
                .toList();

        return CustomResponseDto.success(HttpStatus.OK.value(), projectMemberResponseDtoList);
    }

    @Override
    public CustomResponseDto<List<ProjectMemberResponseDto>> getProjectMembersByProjectId(UUID projectId) {
        List<ProjectMemberResponseDto> projectMembers = projectMemberRepository.getProjectMembersByProjectId(projectId)
                .stream()
                .map(this::convertToDto)
                .toList();

        return CustomResponseDto.success(HttpStatus.OK.value(), projectMembers);
    }

    @Override
    public CustomResponseDto<List<ProjectMemberResponseDto>> getUserMembershipsByUserId(UUID userId) {
        List<ProjectMemberResponseDto> userMembershipInfoList = projectMemberRepository.getMembershipInfosByUserId(userId)
                .stream()
                .map(this::convertToDto)
                .toList();

        return CustomResponseDto.success(HttpStatus.OK.value(), userMembershipInfoList);
    }

    @Override
    public Boolean checkIfUserExistsInProject(UUID projectId, UUID userId) {
        return projectMemberRepository.isUserExistsInProject(projectId, userId);
    }

    @Override
    public CustomResponseDto removeMembership(UUID projectMemberId) {
        ProjectMember projectMember = projectMemberRepository.findByIdAndIsActiveTrue(projectMemberId);
        if (projectMember == null) {
            throw new ProjectMemberNotFound();
        }
        projectMember.setUpdatedAt(new Date());
        projectMember.setActive(false);
        projectMemberRepository.save(projectMember);
        return CustomResponseDto.success(HttpStatus.NO_CONTENT.value());
    }

    //Helper Methods
    public ProjectMemberResponseDto convertToDto(ProjectMember projectMember) {
        return modelMapper.map(projectMember, ProjectMemberResponseDto.class);
    }

    public Project convertToEntity(ProjectMemberRequestDto projectMemberRequestDto) {
        return modelMapper.map(projectMemberRequestDto, Project.class);
    }
}
