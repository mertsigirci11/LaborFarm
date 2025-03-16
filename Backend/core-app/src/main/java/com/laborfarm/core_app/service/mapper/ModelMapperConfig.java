package com.laborfarm.core_app.service.mapper;

import com.laborfarm.core_app.entity.project.Project;
import com.laborfarm.core_app.entity.project.ProjectMember;
import com.laborfarm.core_app.service.dto.ProjectMemberResponseDto;
import com.laborfarm.core_app.service.dto.ProjectResponseDto;
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

        return modelMapper;
    }

}
