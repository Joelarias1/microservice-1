package com.sumativa1joelarias.demo.microservices.forums.service;

import com.sumativa1joelarias.demo.microservices.forums.model.Post;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Servicio para generar datos de prueba para posts
 * Este servicio se utiliza cuando no hay datos en la base de datos
 */
@Service
public class PostTestDataService {
    
    private final Random random = new Random();
    
    /**
     * Genera una lista de posts de prueba
     * @return Lista de posts de prueba
     */
    public List<Post> generateTestPosts() {
        List<Post> testPosts = new ArrayList<>();
        
        // Post 1
        testPosts.add(Post.builder()
                .id(1L)
                .title("Guía definitiva para aprender Angular 18 en 2024")
                .content("Angular 18 trae grandes mejoras en rendimiento y nuevas características que facilitan el desarrollo de aplicaciones modernas. En esta guía exploraremos los conceptos fundamentales, desde la configuración del entorno hasta técnicas avanzadas para optimizar tus aplicaciones. También veremos las mejores prácticas para trabajar con el nuevo sistema de señales y cómo aprovechar al máximo las funcionalidades standalone.")
                .userId(1L)
                .categoryId(1L)
                .status("ACTIVE")
                .createdAt(getRandomDate(7))
                .build());
        
        // Post 2
        testPosts.add(Post.builder()
                .id(2L)
                .title("Comparativa: React vs Angular vs Vue en 2024")
                .content("¿Cuál es el mejor framework frontend en 2024? Analizamos en profundidad las ventajas y desventajas de cada uno, considerando factores como rendimiento, curva de aprendizaje, soporte de la comunidad, y escalabilidad para proyectos de diferentes tamaños. También incluimos ejemplos de código y casos de uso donde cada framework brilla especialmente.")
                .userId(2L)
                .categoryId(1L)
                .status("ACTIVE")
                .createdAt(getRandomDate(14))
                .build());
        
        // Post 3
        testPosts.add(Post.builder()
                .id(3L)
                .title("Implementando microservicios con Spring Boot y Docker")
                .content("En este tutorial, mostramos paso a paso cómo diseñar e implementar una arquitectura de microservicios utilizando Spring Boot, Docker y Kubernetes. Aprenderás patrones de diseño comunes, estrategias de comunicación entre servicios, y técnicas para garantizar la resiliencia y escalabilidad de tu aplicación.")
                .userId(3L)
                .categoryId(2L)
                .status("ACTIVE")
                .createdAt(getRandomDate(3))
                .build());
        
        // Post 4
        testPosts.add(Post.builder()
                .id(4L)
                .title("Mejores prácticas para CI/CD con GitHub Actions")
                .content("GitHub Actions se ha convertido en una herramienta fundamental para la integración y despliegue continuo. En este post, compartimos estrategias avanzadas para configurar flujos de trabajo eficientes, automatizar pruebas, y optimizar el proceso de entrega de software. Incluye ejemplos reales y configuraciones que puedes adaptar a tus proyectos.")
                .userId(4L)
                .categoryId(3L)
                .status("ACTIVE")
                .createdAt(getRandomDate(5))
                .build());
        
        // Post 5
        testPosts.add(Post.builder()
                .id(5L)
                .title("Introducción a LangChain: Construyendo aplicaciones con LLMs")
                .content("LangChain es un framework que simplifica la creación de aplicaciones avanzadas con Modelos de Lenguaje de Gran Escala (LLMs). Este tutorial te guía a través de la configuración inicial, integración con diferentes modelos como GPT-4, y creación de cadenas complejas para aplicaciones de IA generativa con memoria y razonamiento.")
                .userId(5L)
                .categoryId(4L)
                .status("ACTIVE")
                .createdAt(getRandomDate(2))
                .build());
        
        // Post 6
        testPosts.add(Post.builder()
                .id(6L)
                .title("Ofertas de trabajo remoto para desarrolladores en 2024")
                .content("Hemos recopilado las mejores oportunidades de trabajo remoto para desarrolladores este año. Incluimos estadísticas salariales por tecnología, tips para preparar tu portfolio, y estrategias para destacar en entrevistas técnicas. También analizamos qué habilidades son las más demandadas según región y especialidad.")
                .userId(6L)
                .categoryId(5L)
                .status("ACTIVE")
                .createdAt(getRandomDate(1))
                .build());
        
        // Post 7
        testPosts.add(Post.builder()
                .id(7L)
                .title("Optimización avanzada de consultas SQL")
                .content("Técnicas avanzadas para optimizar consultas complejas en bases de datos relacionales. Analizamos el uso de índices, estrategias de particionamiento, y herramientas de análisis de rendimiento para identificar cuellos de botella. Incluye ejemplos prácticos con MySQL, PostgreSQL y Oracle.")
                .userId(7L)
                .categoryId(2L)
                .status("ACTIVE")
                .createdAt(getRandomDate(4))
                .build());
        
        // Post 8
        testPosts.add(Post.builder()
                .id(8L)
                .title("Seguridad en aplicaciones web modernas")
                .content("Una guía completa sobre seguridad para aplicaciones web. Cubrimos los principales vectores de ataque como XSS, CSRF, y SQL Injection, además de mejores prácticas para autenticación, autorización, y protección de datos sensibles. También analizamos herramientas y frameworks que pueden ayudarte a fortalecer la seguridad de tus aplicaciones.")
                .userId(8L)
                .categoryId(2L)
                .status("ACTIVE")
                .createdAt(getRandomDate(6))
                .build());
        
        // Post 9
        testPosts.add(Post.builder()
                .id(9L)
                .title("Experiencias con Kubernetes en producción")
                .content("Lecciones aprendidas después de administrar clusters de Kubernetes en entornos de producción durante más de 3 años. Compartimos configuraciones óptimas, estrategias para gestionar actualizaciones, y técnicas para monitoreo y observabilidad. También abordamos recuperación ante desastres y estrategias de escalado automático.")
                .userId(9L)
                .categoryId(3L)
                .status("ACTIVE")
                .createdAt(getRandomDate(8))
                .build());
        
        return testPosts;
    }
    
    /**
     * Genera un post de prueba específico por ID
     * @param id El ID del post a generar
     * @return El post de prueba con el ID especificado o null si no existe
     */
    public Post getTestPostById(Long id) {
        return generateTestPosts().stream()
                .filter(post -> post.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Genera posts de prueba filtrados por categoría
     * @param categoryId El ID de la categoría
     * @return Lista de posts de prueba de esa categoría
     */
    public List<Post> getTestPostsByCategory(Long categoryId) {
        return generateTestPosts().stream()
                .filter(post -> post.getCategoryId().equals(categoryId))
                .toList();
    }
    
    /**
     * Genera posts de prueba filtrados por usuario
     * @param userId El ID del usuario
     * @return Lista de posts de prueba de ese usuario
     */
    public List<Post> getTestPostsByUser(Long userId) {
        return generateTestPosts().stream()
                .filter(post -> post.getUserId().equals(userId))
                .toList();
    }
    
    /**
     * Busca posts de prueba por término de búsqueda
     * @param query Término de búsqueda
     * @return Lista de posts que coinciden con la búsqueda
     */
    public List<Post> searchTestPosts(String query) {
        String lowerQuery = query.toLowerCase();
        return generateTestPosts().stream()
                .filter(post -> 
                    post.getTitle().toLowerCase().contains(lowerQuery) || 
                    post.getContent().toLowerCase().contains(lowerQuery))
                .toList();
    }
    
    /**
     * Genera una fecha aleatoria entre ahora y hace N días
     * @param daysBack Número máximo de días hacia atrás
     * @return Fecha aleatoria
     */
    private LocalDateTime getRandomDate(int daysBack) {
        LocalDateTime now = LocalDateTime.now();
        int randomDays = random.nextInt(daysBack);
        int randomHours = random.nextInt(24);
        int randomMinutes = random.nextInt(60);
        
        return now.minusDays(randomDays)
                 .minusHours(randomHours)
                 .minusMinutes(randomMinutes);
    }
} 