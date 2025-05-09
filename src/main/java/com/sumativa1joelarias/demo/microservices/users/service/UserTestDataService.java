package com.sumativa1joelarias.demo.microservices.users.service;

import com.sumativa1joelarias.demo.microservices.users.model.User;
import com.sumativa1joelarias.demo.microservices.users.enums.UserRole;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Servicio para generar datos de prueba para usuarios
 */
@Service
public class UserTestDataService {
    
    /**
     * Genera una lista de usuarios de prueba
     * @return Lista de usuarios de prueba
     */
    public List<User> generateTestUsers() {
        List<User> users = new ArrayList<>();
        
        // Usuario 1 - Admin
        users.add(User.builder()
                .id(1L)
                .username("admin")
                .email("admin@example.com")
                .password("$2a$10$wqBfuzFxYAVsYdS5qEO4xu8bNQHbghCyZFwYcjxLACLu8vEJU5bYq") // password: admin123
                .role(UserRole.ADMIN)
                .status("ACTIVE")
                .fullName("Administrador del Sistema")
                .avatar("https://ui-avatars.com/api/?name=Administrador&background=random")
                .createdAt(LocalDateTime.now().minusDays(60))
                .build());
        
        // Usuario 2 - Moderador
        users.add(User.builder()
                .id(2L)
                .username("moderator")
                .email("moderator@example.com")
                .password("$2a$10$wqBfuzFxYAVsYdS5qEO4xu8bNQHbghCyZFwYcjxLACLu8vEJU5bYq") // password: admin123
                .role(UserRole.MODERATOR)
                .status("ACTIVE")
                .fullName("Moderador de Contenido")
                .avatar("https://ui-avatars.com/api/?name=Moderador&background=random")
                .createdAt(LocalDateTime.now().minusDays(55))
                .build());
        
        // Usuario 3 - Usuario regular
        users.add(User.builder()
                .id(3L)
                .username("juandeveloper")
                .email("juan@example.com")
                .password("$2a$10$wqBfuzFxYAVsYdS5qEO4xu8bNQHbghCyZFwYcjxLACLu8vEJU5bYq") // password: admin123
                .role(UserRole.USER)
                .status("ACTIVE")
                .fullName("Juan Pérez")
                .avatar("https://ui-avatars.com/api/?name=Juan+Perez&background=random")
                .createdAt(LocalDateTime.now().minusDays(50))
                .build());
        
        // Usuario 4 - Usuario regular
        users.add(User.builder()
                .id(4L)
                .username("mariafrontend")
                .email("maria@example.com")
                .password("$2a$10$wqBfuzFxYAVsYdS5qEO4xu8bNQHbghCyZFwYcjxLACLu8vEJU5bYq") // password: admin123
                .role(UserRole.USER)
                .status("ACTIVE")
                .fullName("María González")
                .avatar("https://ui-avatars.com/api/?name=Maria+Gonzalez&background=random")
                .createdAt(LocalDateTime.now().minusDays(45))
                .build());
        
        // Usuario 5 - Usuario regular
        users.add(User.builder()
                .id(5L)
                .username("carlosfullstack")
                .email("carlos@example.com")
                .password("$2a$10$wqBfuzFxYAVsYdS5qEO4xu8bNQHbghCyZFwYcjxLACLu8vEJU5bYq") // password: admin123
                .role(UserRole.USER)
                .status("ACTIVE")
                .fullName("Carlos Sánchez")
                .avatar("https://ui-avatars.com/api/?name=Carlos+Sanchez&background=random")
                .createdAt(LocalDateTime.now().minusDays(40))
                .build());
        
        // Usuario 6 - Usuario regular
        users.add(User.builder()
                .id(6L)
                .username("anabackend")
                .email("ana@example.com")
                .password("$2a$10$wqBfuzFxYAVsYdS5qEO4xu8bNQHbghCyZFwYcjxLACLu8vEJU5bYq") // password: admin123
                .role(UserRole.USER)
                .status("ACTIVE")
                .fullName("Ana Rodríguez")
                .avatar("https://ui-avatars.com/api/?name=Ana+Rodriguez&background=random")
                .createdAt(LocalDateTime.now().minusDays(35))
                .build());
        
        // Usuario 7 - Usuario regular
        users.add(User.builder()
                .id(7L)
                .username("pedrodevops")
                .email("pedro@example.com")
                .password("$2a$10$wqBfuzFxYAVsYdS5qEO4xu8bNQHbghCyZFwYcjxLACLu8vEJU5bYq") // password: admin123
                .role(UserRole.USER)
                .status("ACTIVE")
                .fullName("Pedro Martínez")
                .avatar("https://ui-avatars.com/api/?name=Pedro+Martinez&background=random")
                .createdAt(LocalDateTime.now().minusDays(30))
                .build());
        
        // Usuario 8 - Usuario regular
        users.add(User.builder()
                .id(8L)
                .username("lauradata")
                .email("laura@example.com")
                .password("$2a$10$wqBfuzFxYAVsYdS5qEO4xu8bNQHbghCyZFwYcjxLACLu8vEJU5bYq") // password: admin123
                .role(UserRole.USER)
                .status("ACTIVE")
                .fullName("Laura López")
                .avatar("https://ui-avatars.com/api/?name=Laura+Lopez&background=random")
                .createdAt(LocalDateTime.now().minusDays(25))
                .build());
        
        // Usuario 9 - Usuario regular
        users.add(User.builder()
                .id(9L)
                .username("sergiocloud")
                .email("sergio@example.com")
                .password("$2a$10$wqBfuzFxYAVsYdS5qEO4xu8bNQHbghCyZFwYcjxLACLu8vEJU5bYq") // password: admin123
                .role(UserRole.USER)
                .status("ACTIVE")
                .fullName("Sergio Torres")
                .avatar("https://ui-avatars.com/api/?name=Sergio+Torres&background=random")
                .createdAt(LocalDateTime.now().minusDays(20))
                .build());
        
        return users;
    }
    
    /**
     * Obtiene un usuario de prueba por su ID
     * @param id ID del usuario
     * @return El usuario si existe, o un Optional vacío
     */
    public Optional<User> getUserById(Long id) {
        return generateTestUsers().stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();
    }
    
    /**
     * Obtiene un usuario de prueba por su username
     * @param username Nombre de usuario
     * @return El usuario si existe, o un Optional vacío
     */
    public Optional<User> getUserByUsername(String username) {
        return generateTestUsers().stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst();
    }
    
    /**
     * Obtiene un usuario de prueba por su email
     * @param email Email del usuario
     * @return El usuario si existe, o un Optional vacío
     */
    public Optional<User> getUserByEmail(String email) {
        return generateTestUsers().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
    }
} 