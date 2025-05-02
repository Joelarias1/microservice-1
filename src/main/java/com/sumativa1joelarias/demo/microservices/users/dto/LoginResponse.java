package com.sumativa1joelarias.demo.microservices.users.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginResponse {
    private String token;
    private Long userId;
    private String role;
    
    public LoginResponse(String token) {
        this.token = token;
    }
} 