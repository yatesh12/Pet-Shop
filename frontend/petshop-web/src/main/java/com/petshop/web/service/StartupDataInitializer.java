package com.petshop.web.service;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StartupDataInitializer {

    @Bean
    ApplicationRunner seedIdentityData() {
        return args -> {
        };
    }
}

