package com.mibanco.apigastos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tools.jackson.databind.ObjectMapper;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class RepositoryConfig {
    @Bean
    public Path gastosPath() {
        return Paths.get("app/data/gastos.json");
    }
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
