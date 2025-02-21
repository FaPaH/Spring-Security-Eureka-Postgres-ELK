package com.fapah.pokemonservice.config;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class ApplicationConfig {

    @Bean
    public ModelMapper modelMapper() {
        log.info("ModelMapper initialized");
        return new ModelMapper();
    }
}
