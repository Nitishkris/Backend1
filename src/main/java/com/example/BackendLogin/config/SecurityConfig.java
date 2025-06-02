package com.example.BackendLogin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/oauth2/**").permitAll()  // allow OAuth2 endpoints
                        .anyRequest().permitAll()                  // allow other requests (like your custom login)
                )
                .oauth2Login(oauth -> oauth
                        .defaultSuccessUrl("http://localhost:3000/google-success", true)  // frontend success redirect
                )
                .csrf(csrf -> csrf.disable());

        return http.build();
    }
}

