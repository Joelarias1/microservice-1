package com.sumativa1joelarias.demo.microservices.users.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank(message = "El nombre de usuario no puede estar vacío")
    @Size(min = 3, max = 50, message = "El nombre de usuario debe tener entre 3 y 50 caracteres")
    private String username;

    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "Debe ser un formato de email válido")
    @Size(max = 100, message = "El email no puede exceder los 100 caracteres")
    private String email;

    @NotBlank(message = "La contraseña no puede estar vacía")
    // Ejemplo de regex para 4+ reglas: min 8 chars, 1 mayús, 1 minús, 1 número, 1 especial
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,100}$", 
             message = "La contraseña debe tener entre 8 y 100 caracteres, incluyendo al menos una mayúscula, una minúscula, un número y un carácter especial (@$!%*?&)")
    private String password;
    
    // Opcional: Confirmación de contraseña (validación se haría en el servicio)
    // private String confirmPassword;
} 