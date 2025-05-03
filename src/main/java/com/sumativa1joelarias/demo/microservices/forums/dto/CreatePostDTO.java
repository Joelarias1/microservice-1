package com.sumativa1joelarias.demo.microservices.forums.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePostDTO {
    @NotBlank(message = "El título es obligatorio")
    @Size(min = 3, max = 100, message = "El título debe tener entre 3 y 100 caracteres")
    private String title;
    
    @NotBlank(message = "El contenido es obligatorio")
    @Size(min = 10, message = "El contenido debe tener al menos 10 caracteres")
    private String content;
    
    @NotNull(message = "El ID de usuario es obligatorio")
    private Long userId;
    
    @NotNull(message = "La categoría es obligatoria")
    private Long categoryId;
} 