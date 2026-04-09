package com.petshop.web.config;

import com.petshop.web.service.CurrentUserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    private final CurrentUserService currentUserService;

    public RestClientConfig(CurrentUserService currentUserService) {
        this.currentUserService = currentUserService;
    }

    @Bean
    public RestClient catalogRestClient(@Value("${petshop.backend-base-url}") String baseUrl) {
        return RestClient.builder()
                .baseUrl(baseUrl)
                .requestInterceptor((request, body, execution) -> {
                    currentUserService.getAccessToken()
                            .ifPresent(token -> request.getHeaders().set(HttpHeaders.AUTHORIZATION, "Bearer " + token));
                    return execution.execute(request, body);
                })
                .build();
    }

    @Bean
    public RestClient commerceRestClient(@Value("${petshop.backend-base-url}") String baseUrl) {
        return RestClient.builder()
                .baseUrl(baseUrl)
                .requestInterceptor((request, body, execution) -> {
                    currentUserService.getAccessToken()
                            .ifPresent(token -> request.getHeaders().set(HttpHeaders.AUTHORIZATION, "Bearer " + token));
                    return execution.execute(request, body);
                })
                .build();
    }
}
