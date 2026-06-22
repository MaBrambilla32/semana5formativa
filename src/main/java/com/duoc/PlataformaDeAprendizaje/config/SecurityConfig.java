package com.duoc.PlataformaDeAprendizaje.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // 1. Apagamos el CSRF para que te deje hacer POST, PUT y DELETE en Postman sin llorar
            .csrf(csrf -> csrf.disable())

            // 2. Reglas de autorización
            .authorizeHttpRequests(authorize -> authorize
                // OJO ACÁ: Ponemos la ruta exacta Y también la ruta con subcarpetas (/**)
                .requestMatchers("/api/guias", "/api/guias/**").permitAll()
                .anyRequest().authenticated()
            )

            // 3. Tu configuración de JWT de Azure (queda intacta)
            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> {})
            )

            // 4. Apagamos las sesiones para que no genere más JSESSIONID
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            );

        return http.build();
    }
} 
