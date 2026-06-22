package com.duoc.PlataformaDeAprendizaje.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // Exige que todas las peticiones de la plataforma educativa estén autenticadas
            .authorizeHttpRequests(authorize -> authorize
                .anyRequest().authenticated()
            )
            // Configura el backend como Resource Server que valida tokens JWT emitidos por OAuth2 (Azure AD B2C)
            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> {})
            );
            
        return http.build();
    }
}