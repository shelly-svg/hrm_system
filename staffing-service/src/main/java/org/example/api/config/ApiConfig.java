package org.example.api.config;

import org.example.api.mapper.ProjectMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiConfig {

    @Bean
    protected ProjectMapper projectMapper() {
        return Mappers.getMapper(ProjectMapper.class);
    }

}
