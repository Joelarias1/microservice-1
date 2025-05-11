package com.sumativa1joelarias.demo.microservices.forums.controller;

import com.sumativa1joelarias.demo.microservices.forums.model.Category;
import com.sumativa1joelarias.demo.microservices.forums.service.CategoryService;
import com.sumativa1joelarias.demo.microservices.forums.dto.MessageResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    private Category category1;
    private Category category2;
    private List<Category> categories;

    @BeforeEach
    void setUp() {
        category1 = new Category();
        category1.setId(1L);
        category1.setName("Tecnología");
        category1.setDescription("Discusiones sobre tecnología");

        category2 = new Category();
        category2.setId(2L);
        category2.setName("Gaming");
        category2.setDescription("Todo sobre videojuegos");

        categories = Arrays.asList(category1, category2);
    }

    @Test
    void getAllCategories_shouldReturnAllCategories() {
        // Given
        MessageResponse expectedResponse = MessageResponse.success("Categorías obtenidas exitosamente", categories);
        when(categoryService.getAllCategories()).thenReturn(expectedResponse);

        // When
        ResponseEntity<MessageResponse> response = categoryController.getAllCategories();

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isSuccess());
        assertEquals(expectedResponse.getMessage(), response.getBody().getMessage());
        assertEquals(categories, response.getBody().getData());
        verify(categoryService, times(1)).getAllCategories();
    }

    @Test
    void createCategory_shouldCreateAndReturnCategory() {
        // Given
        Category newCategory = new Category();
        newCategory.setName("Nueva Categoría");
        newCategory.setDescription("Nueva descripción");
        
        MessageResponse expectedResponse = MessageResponse.success("Categoría creada exitosamente", newCategory);
        when(categoryService.createCategory(any(Category.class))).thenReturn(expectedResponse);

        // When
        ResponseEntity<MessageResponse> response = categoryController.createCategory(newCategory);

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isSuccess());
        assertEquals(expectedResponse.getMessage(), response.getBody().getMessage());
        assertEquals(newCategory, response.getBody().getData());
        verify(categoryService, times(1)).createCategory(any(Category.class));
    }

    @Test
    void getCategoryById_whenExists_shouldReturnCategory() {
        // Given
        MessageResponse expectedResponse = MessageResponse.success("Categoría encontrada exitosamente", category1);
        when(categoryService.getCategoryById(1L)).thenReturn(expectedResponse);

        // When
        ResponseEntity<MessageResponse> response = categoryController.getCategoryById(1L);

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isSuccess());
        assertEquals(expectedResponse.getMessage(), response.getBody().getMessage());
        assertEquals(category1, response.getBody().getData());
        verify(categoryService, times(1)).getCategoryById(1L);
    }

    @Test
    void getCategoryById_whenNotExists_shouldReturnError() {
        // Given
        MessageResponse expectedResponse = MessageResponse.error("Categoría no encontrada");
        when(categoryService.getCategoryById(99L)).thenReturn(expectedResponse);

        // When
        ResponseEntity<MessageResponse> response = categoryController.getCategoryById(99L);

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isSuccess());
        assertEquals("Categoría no encontrada", response.getBody().getMessage());
        verify(categoryService, times(1)).getCategoryById(99L);
    }

    @Test
    void updateCategory_whenExists_shouldUpdateAndReturnCategory() {
        // Given
        Category updatedCategory = new Category();
        updatedCategory.setName("Categoría Actualizada");
        updatedCategory.setDescription("Descripción actualizada");
        
        MessageResponse expectedResponse = MessageResponse.success("Categoría actualizada exitosamente", updatedCategory);
        when(categoryService.updateCategory(eq(1L), any(Category.class))).thenReturn(expectedResponse);

        // When
        ResponseEntity<MessageResponse> response = categoryController.updateCategory(1L, updatedCategory);

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isSuccess());
        assertEquals(expectedResponse.getMessage(), response.getBody().getMessage());
        assertEquals(updatedCategory, response.getBody().getData());
        verify(categoryService, times(1)).updateCategory(eq(1L), any(Category.class));
    }

    @Test
    void deleteCategory_whenExists_shouldReturnSuccess() {
        // Given
        MessageResponse expectedResponse = MessageResponse.success("Categoría eliminada exitosamente");
        when(categoryService.deleteCategory(1L)).thenReturn(expectedResponse);

        // When
        ResponseEntity<MessageResponse> response = categoryController.deleteCategory(1L);

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isSuccess());
        assertEquals("Categoría eliminada exitosamente", response.getBody().getMessage());
        verify(categoryService, times(1)).deleteCategory(1L);
    }
} 