package com.sumativa1joelarias.demo.microservices.users.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String token;
    // Podrías añadir más campos si los necesitas en el frontend,
    // como el rol o el id del usuario.
} 