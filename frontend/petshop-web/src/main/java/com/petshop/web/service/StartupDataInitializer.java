package com.petshop.web.service;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class StartupDataInitializer {

    private final UserAccountService userAccountService;

    @Bean
    ApplicationRunner seedIdentityData() {
        return args -> userAccountService.ensureBaselineData();
    }
}

