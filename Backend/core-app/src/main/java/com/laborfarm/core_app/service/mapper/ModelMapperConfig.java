package com.laborfarm.core_app.service.mapper;

import com.laborfarm.core_app.entity.project.Project;
import com.laborfarm.core_app.entity.project.ProjectMember;
import com.laborfarm.core_app.entity.task.FileInfo;
import com.laborfarm.core_app.entity.task.Task;
import com.laborfarm.core_app.entity.task.TaskComment;
import com.laborfarm.core_app.service.dto.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.addMappings(new PropertyMap<Project, ProjectResponseDto>() {
            @Override
            protected void configure() {
                map(source.getDepartmentId(), destination.getDepartmentId());
                map(source.getStatusId(), destination.getStatusId());
            }
        });

        modelMapper.addMappings(new PropertyMap<ProjectMember, ProjectMemberResponseDto>() {
            @Override
            protected void configure() {
                map().setUserId(source.getUser().getId());
                map().setProjectId(source.getProject().getId());
            }
        });

        modelMapper.addMappings(new PropertyMap<Task, TaskResponseDto>() {
            @Override
            protected void configure() {
                map().setId(source.getId());
                map().setStateId(source.getStateId());
                map().setPriorityId(source.getPriorityId());
                map().setProjectId(source.getProjectId());
                map().setAssigneeId(source.getAssigneeId());
            }
        });

        modelMapper.addMappings(new PropertyMap<TaskComment, TaskCommentResponseDto>(){
            @Override
            protected void configure() {
                map().setId(source.getId());
                map().setCreatorUserId(source.getCreatorUserId());
                map().setTaskId(source.getTaskId());
            }
        });
        
        modelMapper.addMappings(new PropertyMap<FileInfo, FileInfoResponseDto>() {
            @Override
            protected void configure() {
                map().setTaskId(source.getTaskId());
                map().setUserId(source.getUserId());
            }
        });

        return modelMapper;
    }

}
