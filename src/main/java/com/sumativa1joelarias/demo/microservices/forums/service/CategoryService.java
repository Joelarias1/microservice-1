package com.sumativa1joelarias.demo.microservices.forums.service;

import com.sumativa1joelarias.demo.microservices.forums.model.Category;
import com.sumativa1joelarias.demo.microservices.forums.repository.CategoryRepository;
import com.sumativa1joelarias.demo.microservices.forums.dto.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

/**
 * Servicio para manejar operaciones relacionadas con categorías
 */
@Service
public class CategoryService {
    
    private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    @Autowired
    private CategoryTestDataService testDataService;
    
    // Variable para controlar si se deben usar datos de prueba siempre
    private boolean alwaysUseTestData = true;
    
    /**
     * Obtiene todas las categorías
     * @return Respuesta con las categorías
     */
    public MessageResponse getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        
        // Si siempre queremos usar datos de prueba o no hay categorías en la base de datos
        if (alwaysUseTestData || categories.isEmpty()) {
            logger.info("Usando datos de prueba para getAllCategories");
            categories = testDataService.generateTestCategories();
        }
        
        return MessageResponse.success("Categorías obtenidas exitosamente", categories);
    }
    
    /**
     * Crea una nueva categoría
     * @param category La categoría a crear
     * @return Respuesta con la categoría creada
     */
    public MessageResponse createCategory(Category category) {
        Category savedCategory = categoryRepository.save(category);
        return MessageResponse.success("Categoría creada exitosamente", savedCategory);
    }
    
    /**
     * Obtiene una categoría por su ID
     * @param id ID de la categoría a buscar
     * @return Respuesta con la categoría encontrada o error si no existe
     */
    public MessageResponse getCategoryById(Long id) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        
        // Si siempre queremos usar datos de prueba o no se encontró la categoría
        if (alwaysUseTestData || !categoryOptional.isPresent()) {
            logger.info("Usando datos de prueba para getCategoryById");
            Optional<Category> testCategory = testDataService.getCategoryById(id);
            
            return testCategory
                    .map(category -> MessageResponse.success("Categoría de prueba encontrada exitosamente", category))
                    .orElse(MessageResponse.error("Categoría no encontrada"));
        }
        
        return categoryOptional
                .map(category -> MessageResponse.success("Categoría encontrada exitosamente", category))
                .orElse(MessageResponse.error("Categoría no encontrada"));
    }
    
    /**
     * Actualiza una categoría existente
     * @param id ID de la categoría a actualizar
     * @param categoryDetails Nuevos datos para la categoría
     * @return Respuesta con la categoría actualizada o error si no existe
     */
    public MessageResponse updateCategory(Long id, Category categoryDetails) {
        return categoryRepository.findById(id)
                .map(category -> {
                    category.setName(categoryDetails.getName());
                    category.setDescription(categoryDetails.getDescription());
                    Category updatedCategory = categoryRepository.save(category);
                    return MessageResponse.success("Categoría actualizada exitosamente", updatedCategory);
                })
                .orElse(MessageResponse.error("Categoría no encontrada"));
    }
    
    /**
     * Elimina una categoría
     * @param id ID de la categoría a eliminar
     * @return Respuesta con resultado de la operación
     */
    public MessageResponse deleteCategory(Long id) {
        return categoryRepository.findById(id)
                .map(category -> {
                    categoryRepository.delete(category);
                    return MessageResponse.success("Categoría eliminada exitosamente");
                })
                .orElse(MessageResponse.error("Categoría no encontrada"));
    }
} 