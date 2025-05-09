package com.sumativa1joelarias.demo.microservices.users.controller;

import com.sumativa1joelarias.demo.microservices.users.dto.UserDTO;
import com.sumativa1joelarias.demo.microservices.users.dto.UserManagementRequest;
import com.sumativa1joelarias.demo.microservices.users.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/users") // Endpoint base para la gestión de usuarios
public class UserController {

    private final UserService userService;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserController(UserService userService, JdbcTemplate jdbcTemplate) {
        this.userService = userService;
        this.jdbcTemplate = jdbcTemplate;
    }

    // Endpoint de prueba
    @GetMapping("/test")
    public ResponseEntity<Map<String, String>> testEndpoint() {
        Map<String, String> response = Collections.singletonMap("message", "El endpoint de usuarios está funcionando correctamente");
        return ResponseEntity.ok(response);
    }

    // GET /api/users - Obtener todos los usuarios
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // GET /api/users/{id} - Obtener un usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        UserDTO user = userService.getUserById(id);
        return ResponseEntity.ok(user);
        // La excepción ResourceNotFoundException se manejará automáticamente 
        // gracias a @ResponseStatus en la clase de excepción
    }

    // POST /api/users - Crear un nuevo usuario
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserManagementRequest request) {
        UserDTO createdUser = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    // PUT /api/users/{id} - Actualizar un usuario existente
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserManagementRequest request) {
        UserDTO updatedUser = userService.updateUser(id, request);
        return ResponseEntity.ok(updatedUser);
    }

    // PUT /api/users/profile - Actualizar perfil del usuario autenticado
    @PutMapping("/profile")
    public ResponseEntity<UserDTO> updateProfile(@Valid @RequestBody UserManagementRequest request) {
        // Aquí obtenemos el ID del usuario desde el token JWT o la sesión
        // Para propósitos de prueba, usamos el ID 1
        Long userId = 1L; 
        
        UserDTO updatedUser = userService.updateProfile(userId, request);
        return ResponseEntity.ok(updatedUser);
    }

    // GET /api/users/profile - Obtener perfil del usuario autenticado
    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getProfile() {
        // Aquí obtenemos el ID del usuario desde el token JWT o la sesión
        // Para propósitos de prueba, usamos el ID 1
        Long userId = 1L;
        
        UserDTO user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    // POST /api/users/change-password - Cambiar contraseña del usuario
    @PostMapping("/change-password")
    public ResponseEntity<Map<String, Boolean>> changePassword(@RequestBody Map<String, String> passwordData) {
        // Aquí obtenemos el ID del usuario desde el token JWT o la sesión
        // Para propósitos de prueba, usamos el ID 1
        Long userId = 1L;
        
        // Validar que los datos necesarios estén presentes
        if (!passwordData.containsKey("currentPassword") || !passwordData.containsKey("newPassword")) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("success", false));
        }
        
        // Crear un request para actualizar solo la contraseña
        UserManagementRequest request = new UserManagementRequest();
        request.setPassword(passwordData.get("newPassword"));
        
        // En una implementación real, verificaríamos la contraseña actual primero
        // Por ahora, solo actualizamos la contraseña
        userService.updateProfile(userId, request);
        
        return ResponseEntity.ok(Collections.singletonMap("success", true));
    }

    // PUT /api/users/preferences - Actualizar preferencias del usuario
    @PutMapping("/preferences")
    public ResponseEntity<Map<String, Boolean>> updatePreferences(@RequestBody Map<String, Object> preferences) {
        // Aquí obtenemos el ID del usuario desde el token JWT o la sesión
        // Para propósitos de prueba, usamos el ID 1
        Long userId = 1L;
        
        // En una implementación real, actualizaríamos las preferencias en la base de datos
        // Por ahora, solo devolvemos una respuesta exitosa
        
        return ResponseEntity.ok(Collections.singletonMap("success", true));
    }

    // DELETE /api/users/{id} - Eliminar un usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable Long id) {
        try {
            // Intentar primero eliminar cualquier restricción de integridad referencial
            // Primero eliminamos comentarios
            int commentsDeleted = jdbcTemplate.update("DELETE FROM comments WHERE user_id = ?", id);
            
            // Luego eliminamos posts
            int postsDeleted = jdbcTemplate.update("DELETE FROM posts WHERE user_id = ?", id);
            
            // Finalmente eliminamos el usuario
            userService.deleteUser(id);
            
            Map<String, String> response = new HashMap<>();
            response.put("success", "true");
            response.put("message", "Usuario eliminado correctamente");
            response.put("details", String.format("Se eliminaron %d comentarios y %d posts", commentsDeleted, postsDeleted));
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("success", "false");
            response.put("message", "Error al eliminar usuario: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
} 