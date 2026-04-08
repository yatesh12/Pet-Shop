package com.petshop.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {
        "com.petshop.catalog.controller",
        "com.petshop.catalog.service",
        "com.petshop.catalog.repository",
        "com.petshop.catalog.mapper",
        "com.petshop.catalog.exception",
        "com.petshop.catalog.entity",
        "com.petshop.commerce.controller",
        "com.petshop.commerce.service",
        "com.petshop.commerce.repository",
        "com.petshop.commerce.mapper",
        "com.petshop.commerce.exception",
        "com.petshop.commerce.entity",
        "com.petshop.shared"
})
@EnableJpaRepositories(basePackages = {
        "com.petshop.catalog.repository",
        "com.petshop.commerce.repository"
})
@EntityScan(basePackages = {
        "com.petshop.catalog.entity",
        "com.petshop.commerce.entity"
})
public class PetShopBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(PetShopBackendApplication.class, args);
    }
}
