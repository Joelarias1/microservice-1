package com.sumativa1joelarias.demo.microservices.users.controller;

import com.sumativa1joelarias.demo.microservices.users.dto.LoginRequest;
import com.sumativa1joelarias.demo.microservices.users.dto.LoginResponse;
import com.sumativa1joelarias.demo.microservices.users.dto.RegisterRequest;
import com.sumativa1joelarias.demo.microservices.users.model.User;
import com.sumativa1joelarias.demo.microservices.users.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;

// Importaciones básicas para JWT (simplificado)
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final Key jwtSecretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        
        String identifier = loginRequest.getIdentifier();
        System.out.println("Intento de login para identifier: " + identifier);
        
        User user = null;
        
        // Verificamos si el identifier contiene @ para determinar si es un email
        if (identifier.contains("@")) {
            System.out.println("Identificador detectado como email: " + identifier);
            user = userRepository.findByEmail(identifier).orElse(null);
        } else {
            System.out.println("Identificador detectado como username: " + identifier);
            user = userRepository.findByUsername(identifier).orElse(null);
        }

        if (user == null) {
            System.out.println("Usuario no encontrado para identifier: " + identifier);
            Map<String, String> errorResponse = Collections.singletonMap("error", "Usuario o contraseña incorrectos");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse); 
        }

        System.out.println("Usuario encontrado: " + user.getUsername());
        System.out.println("Contraseña recibida: [" + loginRequest.getPassword() + "]"); 
        System.out.println("Contraseña almacenada: [" + user.getPassword() + "]"); 
        System.out.println("Rol del usuario: " + user.getRole().toString());
        System.out.println("ID del usuario: " + user.getId());

        // Usar comparación directa .equals()
        boolean passwordMatch = loginRequest.getPassword().equals(user.getPassword());
        System.out.println("Resultado de comparación directa: " + passwordMatch);
        
        if (passwordMatch) {
            System.out.println("Contraseña coincide. Generando token...");
            // Generar un token JWT simple con más información
            String token = Jwts.builder()
                               .setSubject(user.getEmail())
                               .claim("userId", user.getId())
                               .claim("role", user.getRole().toString())
                               .setIssuedAt(new Date())
                               .signWith(jwtSecretKey)
                               .compact();
            
            // Creamos una respuesta más completa con token, userId y role
            LoginResponse response = new LoginResponse(token);
            response.setUserId(user.getId());
            response.setRole(user.getRole().toString());
            
            System.out.println("Respuesta de login a enviar: token=" + (token != null ? token.substring(0, 15) + "..." : "null") 
                             + ", userId=" + user.getId() 
                             + ", role=" + user.getRole().toString());
            
            return ResponseEntity.ok(response);
        } else {
             System.out.println("Contraseña NO coincide.");
             Map<String, String> errorResponse = Collections.singletonMap("error", "Usuario o contraseña incorrectos");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse); 
        }
    }

    // --- Endpoint de Registro --- 
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
             Map<String, String> errorResponse = Collections.singletonMap("error", "El nombre de usuario ya está en uso.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
             Map<String, String> errorResponse = Collections.singletonMap("error", "El correo electrónico ya está registrado.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        User newUser = new User();
        newUser.setUsername(registerRequest.getUsername());
        newUser.setEmail(registerRequest.getEmail());
        // Guardar contraseña en texto plano (INSEGURO - SOLO PARA TEST)
        newUser.setPassword(registerRequest.getPassword()); 
        newUser.setRole(com.sumativa1joelarias.demo.microservices.users.enums.UserRole.ADMIN); // Asignamos rol ADMIN por mientras
        newUser.setStatus("ACTIVE");

        try {
            userRepository.save(newUser);
             Map<String, String> successResponse = Collections.singletonMap("message", "Usuario registrado exitosamente.");
            return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
        } catch (Exception e) {
             System.err.println("Error al guardar nuevo usuario: " + e.getMessage());
             Map<String, String> errorResponse = Collections.singletonMap("error", "Ocurrió un error interno al registrar el usuario.");
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
} 