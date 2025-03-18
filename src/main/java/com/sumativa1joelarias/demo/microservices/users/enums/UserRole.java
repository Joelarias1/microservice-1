package com.sumativa1joelarias.demo.microservices.users.enums;

public enum UserRole {
    ADMIN,     // Puede hacer todo
    MODERATOR, // Puede moderar posts y comentarios, pero no administrar usuarios
    USER       // Solo puede crear/editar sus propios posts y comentarios
} 