package com.sumativa1joelarias.demo.microservices.forums.service;

import com.sumativa1joelarias.demo.microservices.forums.model.Category;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Servicio para generar datos de prueba de categorías
 */
@Service
public class CategoryTestDataService {
    
    /**
     * Genera una lista de categorías de prueba
     * @return Lista de categorías de prueba
     */
    public List<Category> generateTestCategories() {
        List<Category> categories = new ArrayList<>();
        
        // Categoría 1
        categories.add(Category.builder()
                .id(1L)
                .name("Desarrollo Frontend")
                .description("HTML, CSS, JavaScript, frameworks y librerías")
                .createdAt(LocalDateTime.now().minusDays(30))
                .build());
        
        // Categoría 2
        categories.add(Category.builder()
                .id(2L)
                .name("Desarrollo Backend")
                .description("Lenguajes de servidor, bases de datos y arquitectura")
                .createdAt(LocalDateTime.now().minusDays(30))
                .build());
        
        // Categoría 3
        categories.add(Category.builder()
                .id(3L)
                .name("DevOps y Cloud")
                .description("Contenedores, CI/CD, infraestructura y servicios cloud")
                .createdAt(LocalDateTime.now().minusDays(29))
                .build());
        
        // Categoría 4
        categories.add(Category.builder()
                .id(4L)
                .name("Inteligencia Artificial")
                .description("Machine learning, deep learning y MLOps")
                .createdAt(LocalDateTime.now().minusDays(28))
                .build());
        
        // Categoría 5
        categories.add(Category.builder()
                .id(5L)
                .name("Comunidad y Networking")
                .description("Eventos, oportunidades y colaboración")
                .createdAt(LocalDateTime.now().minusDays(27))
                .build());
        
        return categories;
    }
    
    /**
     * Obtiene una categoría de prueba por su ID
     * @param id ID de la categoría
     * @return La categoría si existe, o un Optional vacío
     */
    public Optional<Category> getCategoryById(Long id) {
        return generateTestCategories().stream()
                .filter(category -> category.getId().equals(id))
                .findFirst();
    }
} 