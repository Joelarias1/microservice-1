package com.sumativa1joelarias.demo.microservices.users.dto;

import com.sumativa1joelarias.demo.microservices.users.enums.UserRole;
import lombok.Data;
import java.time.LocalDateTime;

@Data // Lombok annotation for getters, setters, etc.
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private UserRole role;
    private String status;
    // Incluyendo password en DTO solo para entorno de prueba
    private String password;
    
    // Nuevos campos para la aplicación frontend
    private String fullName;
    private String avatar;
    private LocalDateTime createdAt;
} 