package com.sumativa1joelarias.demo.microservices.users.dto;

import com.sumativa1joelarias.demo.microservices.users.enums.UserRole;
import lombok.Data;

@Data // Lombok annotation for getters, setters, etc.
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private UserRole role;
    private String status;
    // Incluyendo password en DTO solo para entorno de prueba
    private String password;
} 