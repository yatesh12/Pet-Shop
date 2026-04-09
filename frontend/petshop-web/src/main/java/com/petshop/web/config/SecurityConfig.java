package com.petshop.web.config;

import com.petshop.web.security.BackendAuthenticationProvider;
import com.petshop.web.security.BackendLogoutHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   BackendAuthenticationProvider backendAuthenticationProvider,
                                                   BackendLogoutHandler backendLogoutHandler) throws Exception {
        http
                .authenticationProvider(backendAuthenticationProvider)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/shop/**", "/pets/**", "/services/**", "/about", "/contact", "/faq", "/blog/**", "/register", "/login", "/css/**", "/js/**", "/images/**", "/webjars/**", "/error").permitAll()
                        .requestMatchers("/account/**", "/cart/**", "/checkout/**").authenticated()
                        .requestMatchers("/admin/**").hasAnyRole("ADMIN", "MANAGER")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/account", true)
                        .failureUrl("/login?error")
                        .permitAll())
                .logout(logout -> logout
                        .addLogoutHandler(backendLogoutHandler)
                        .logoutSuccessUrl("/login?logout")
                        .permitAll());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

