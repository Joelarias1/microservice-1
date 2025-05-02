package com.sumativa1joelarias.demo; // Ubicado en el paquete principal

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
// @EnableWebSecurity ELIMINADO
public class WebConfig {

    // La configuración CORS con WebMvcConfigurer puede coexistir o reemplazarse
    // por la configuración CORS en SecurityConfig. Dejémosla por ahora, 
    // pero la de SecurityConfig tiene precedencia si ambas están.
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**") // Permite CORS para todas las rutas bajo /api
                        .allowedOrigins("http://localhost:4200") // Permite explícitamente el origen del frontend Angular
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Métodos HTTP permitidos
                        .allowedHeaders("*") // Cabeceras permitidas
                        .allowCredentials(true); // Permite credenciales (cookies, etc.) si las usas
            }
        };
    }

    // Bean PasswordEncoder (se mantiene aquí, es útil)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Bean SecurityFilterChain ELIMINADO

} 