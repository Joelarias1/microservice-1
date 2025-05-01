package com.sumativa1joelarias.demo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/health")
public class HealthCheckController {

    private final DataSource dataSource;

    // Inyectar el DataSource configurado
    public HealthCheckController(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @GetMapping("/db")
    public ResponseEntity<Map<String, String>> checkDbConnection() {
        try (Connection connection = dataSource.getConnection()) {
            // Si obtener la conexión no lanza excepción, asumimos que está UP
            if (connection.isValid(1)) { // Opcional: validar la conexión con timeout de 1 seg
                 return ResponseEntity.ok(Collections.singletonMap("status", "UP"));
            } else {
                 return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                                     .body(Collections.singletonMap("status", "DOWN"));
            }
        } catch (Exception e) {
            // Si hay cualquier excepción al obtener/validar la conexión
            System.err.println("DB Health Check Failed: " + e.getMessage()); // Loggear el error
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                                 .body(Collections.singletonMap("status", "DOWN"));
        }
    }
} 