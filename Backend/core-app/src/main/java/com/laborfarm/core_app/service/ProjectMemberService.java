package com.laborfarm.core_app.service;

import com.laborfarm.core_app.service.dto.CustomResponseDto;
import com.laborfarm.core_app.service.dto.ProjectMemberRequestDto;
import com.laborfarm.core_app.service.dto.ProjectMemberResponseDto;

import java.util.List;
import java.util.UUID;

public interface ProjectMemberService {
    CustomResponseDto<ProjectMemberResponseDto> addProjectMember(ProjectMemberRequestDto projectMemberRequestDto);
    CustomResponseDto<List<ProjectMemberResponseDto>> getAllProjectMembers();
    CustomResponseDto<List<ProjectMemberResponseDto>> getProjectMembersByProjectId(UUID projectId);
    CustomResponseDto<List<ProjectMemberResponseDto>> getUserMembershipsByUserId(UUID userId);
    Boolean checkIfUserExistsInProject(UUID projectId, UUID userId);
    CustomResponseDto removeMembership(UUID projectMemberId);
}