package org.example.api.config;

import org.example.api.mapper.TokenMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiConfig {

    @Bean
    public TokenMapper tokenMapper() {
        return Mappers.getMapper(TokenMapper.class);
    }

}
