package com.sumativa1joelarias.demo.microservices.users.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    
    @NotBlank(message = "El identificador no puede estar vacío")
    private String identifier; // Puede ser username o email
    
    @NotBlank(message = "La contraseña no puede estar vacía")
    private String password;
} 