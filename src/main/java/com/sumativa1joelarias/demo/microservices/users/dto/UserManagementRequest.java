package com.sumativa1joelarias.demo.microservices.users.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import com.sumativa1joelarias.demo.microservices.users.enums.UserRole;

@Data
public class UserManagementRequest {

    @NotBlank(message = "El nombre de usuario no puede estar vacío")
    @Size(min = 3, max = 50)
    private String username;

    @NotBlank(message = "El email no puede estar vacío")
    @Email
    @Size(max = 100)
    private String email;

    // La contraseña es opcional en la actualización, 
    // la validación de requerida se manejaría en el servicio si es creación
    @Size(max = 100) // Podríamos añadir @Pattern aquí si quisiéramos forzar complejidad también
    private String password;

    // Añadir campo para el rol
    private UserRole role;
    
    // Añadir campo para el estado
    private String status;
} 