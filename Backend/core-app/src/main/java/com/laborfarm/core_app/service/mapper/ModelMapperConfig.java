package com.laborfarm.core_app.service.mapper;

import com.laborfarm.core_app.entity.project.Project;
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

        return modelMapper;
    }

}
