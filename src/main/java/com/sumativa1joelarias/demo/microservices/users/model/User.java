package com.sumativa1joelarias.demo.microservices.users.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import com.sumativa1joelarias.demo.microservices.users.enums.UserRole;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String username;
    
    @Column(nullable = false)
    private String password;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;
    
    @Column(nullable = false)
    private String status;
    
    // Nuevos campos para la aplicación frontend
    @Column(name = "full_name")
    private String fullName;
    
    @Column(name = "avatar", columnDefinition = "TEXT")
    private String avatar;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    // Métodos helper para verificar roles
    public boolean isAdmin() {
        return role == UserRole.ADMIN;
    }

    public boolean isModerator() {
        return role == UserRole.MODERATOR;
    }

    public boolean isUser() {
        return role == UserRole.USER;
    }

    public boolean canModerate() {
        return isAdmin() || isModerator();
    }
} 