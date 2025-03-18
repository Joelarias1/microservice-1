package com.sumativa1joelarias.demo.microservices.forums.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
    private Long id;
    private String title;
    private String content;
    private Long userId;
    private String username;
    private Long categoryId;
    private String categoryName;
    private LocalDateTime createdAt;
    private String status;
} 