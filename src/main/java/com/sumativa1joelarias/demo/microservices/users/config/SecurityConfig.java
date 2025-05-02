package com.sumativa1joelarias.demo.microservices.users.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    public SecurityConfig() {
        System.out.println("\n*****************************************************");
        System.out.println("**       SecurityConfig Bean INSTANCIADO        **");
        System.out.println("*****************************************************\n");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // Aplicar configuración CORS obtenida del bean corsConfigurationSource
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            // Deshabilitar CSRF
            .csrf(csrf -> csrf.disable())
            // Configurar reglas de autorización usando Lambda DSL
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll() // PERMITIR TODO TEMPORALMENTE
            )
            // Deshabilitar autenticación HTTP Basic
            .httpBasic(basic -> basic.disable());

        return http.build();
    }

    // Bean para configuración de CORS global
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        config.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
} 