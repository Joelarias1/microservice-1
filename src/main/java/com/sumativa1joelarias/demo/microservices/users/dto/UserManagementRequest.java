package com.sumativa1joelarias.demo.microservices.users.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserManagementRequest {

    @NotBlank(message = "Username cannot be blank")
    @Size(min = 3, max = 50)
    private String username;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    @Size(max = 100)
    private String email;

    // Password validation removed for simplicity in testing environment
    @NotBlank(message = "Password cannot be blank")
    private String password;

    // Could add Role and Status fields here if admins should set them directly
    // private com.sumativa1joelarias.demo.microservices.users.enums.UserRole role;
    // private String status;
} 