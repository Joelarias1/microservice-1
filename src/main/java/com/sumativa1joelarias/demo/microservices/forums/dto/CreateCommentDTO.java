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
public class CreateCommentDTO {
    @NotBlank(message = "El contenido es obligatorio")
    @Size(min = 3, max = 1000, message = "El comentario debe tener entre 3 y 1000 caracteres")
    private String content;
    
    @NotNull(message = "El ID del post es obligatorio")
    private Long postId;
    
    @NotNull(message = "El ID de usuario es obligatorio")
    private Long userId;
} 