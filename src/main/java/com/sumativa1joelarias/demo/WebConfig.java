package com.sumativa1joelarias.demo; // Ubicado en el paquete principal

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {


    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**") // Permite CORS para todas las rutas bajo /api
                        .allowedOrigins(
                                "http://localhost:4200",
                                "http://192.168.1.100:4200",
                                "http://localhost:8080",
                                "http://192.168.1.100:8080"

                                // "http://tu-ip-local:puerto-frontend"
                                ) 
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH") // Métodos HTTP permitidos
                        .allowedHeaders("*") // Cabeceras permitidas
                        .allowCredentials(true) // Permite credenciales (cookies, etc.) si las usas
                        .maxAge(3600); // Opcional: tiempo de caché para la respuesta preflight
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