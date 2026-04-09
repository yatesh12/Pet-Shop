package com.petshop.identity.service;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class IdentityStartupDataInitializer {

    private final IdentityAuthService identityAuthService;

    @Bean
    ApplicationRunner seedIdentityData() {
        return args -> identityAuthService.ensureBaselineData();
    }
}
