package com.sumativa1joelarias.demo.microservices.users.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.sumativa1joelarias.demo.microservices.users.enums.UserRole;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String password;
    private UserRole role;
    private String status;
    private LocalDateTime createdAt;
} 