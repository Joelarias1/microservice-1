package com.sumativa1joelarias.demo.microservices.forums.service;

import com.sumativa1joelarias.demo.microservices.forums.dto.MessageResponse;
import com.sumativa1joelarias.demo.microservices.forums.model.Category;
import com.sumativa1joelarias.demo.microservices.forums.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryTestDataService testDataService;

    @InjectMocks
    private CategoryService categoryService;

    private Category testCategory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testCategory = new Category();
        testCategory.setId(1L);
        testCategory.setName("Test Category");
        testCategory.setDescription("Test Description");
    }

    @Test
    void getAllCategories_WithRealData() {
        // Given
        when(categoryRepository.findAll()).thenReturn(Arrays.asList(testCategory));

        // When
        MessageResponse response = categoryService.getAllCategories();

        // Then
        assertTrue(response.isSuccess());
        assertNotNull(response.getData());
        verify(testDataService, never()).generateTestCategories();
    }

    @Test
    void getAllCategories_EmptyDB_UsesTestData() {
        // Given
        when(categoryRepository.findAll()).thenReturn(Collections.emptyList());
        when(testDataService.generateTestCategories()).thenReturn(Arrays.asList(testCategory));

        // When
        MessageResponse response = categoryService.getAllCategories();

        // Then
        assertTrue(response.isSuccess());
        assertNotNull(response.getData());
        verify(testDataService).generateTestCategories();
    }

    @Test
    void createCategory_Success() {
        // Given
        when(categoryRepository.save(any(Category.class))).thenReturn(testCategory);

        // When
        MessageResponse response = categoryService.createCategory(testCategory);

        // Then
        assertTrue(response.isSuccess());
        assertNotNull(response.getData());
        assertEquals("Categoría creada exitosamente", response.getMessage());
        verify(categoryRepository).save(testCategory);
    }

    @Test
    void getCategoryById_ExistingCategory() {
        // Given
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(testCategory));

        // When
        MessageResponse response = categoryService.getCategoryById(1L);

        // Then
        assertTrue(response.isSuccess());
        assertNotNull(response.getData());
        assertEquals("Categoría encontrada exitosamente", response.getMessage());
        verify(testDataService, never()).getCategoryById(any());
    }

    @Test
    void getCategoryById_NotFound_UsesTestData() {
        // Given
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());
        when(testDataService.getCategoryById(1L)).thenReturn(Optional.of(testCategory));

        // When
        MessageResponse response = categoryService.getCategoryById(1L);

        // Then
        assertTrue(response.isSuccess());
        assertNotNull(response.getData());
        assertEquals("Categoría de prueba encontrada exitosamente", response.getMessage());
        verify(testDataService).getCategoryById(1L);
    }

    @Test
    void updateCategory_Success() {
        // Given
        Category updatedCategory = new Category();
        updatedCategory.setName("Updated Name");
        updatedCategory.setDescription("Updated Description");

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(testCategory));
        when(categoryRepository.save(any(Category.class))).thenReturn(updatedCategory);

        // When
        MessageResponse response = categoryService.updateCategory(1L, updatedCategory);

        // Then
        assertTrue(response.isSuccess());
        assertNotNull(response.getData());
        assertEquals("Categoría actualizada exitosamente", response.getMessage());
        verify(categoryRepository).save(any(Category.class));
    }

    @Test
    void updateCategory_NotFound() {
        // Given
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        // When
        MessageResponse response = categoryService.updateCategory(1L, testCategory);

        // Then
        assertFalse(response.isSuccess());
        assertEquals("Categoría no encontrada", response.getMessage());
        verify(categoryRepository, never()).save(any(Category.class));
    }

    @Test
    void deleteCategory_Success() {
        // Given
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(testCategory));
        doNothing().when(categoryRepository).delete(testCategory);

        // When
        MessageResponse response = categoryService.deleteCategory(1L);

        // Then
        assertTrue(response.isSuccess());
        assertEquals("Categoría eliminada exitosamente", response.getMessage());
        verify(categoryRepository).delete(testCategory);
    }

    @Test
    void deleteCategory_NotFound() {
        // Given
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        // When
        MessageResponse response = categoryService.deleteCategory(1L);

        // Then
        assertFalse(response.isSuccess());
        assertEquals("Categoría no encontrada", response.getMessage());
        verify(categoryRepository, never()).delete(any());
    }

    @Test
    void setAlwaysUseTestData_AffectsGetAll() {
        // Given
        categoryService.setAlwaysUseTestData(true);
        when(testDataService.generateTestCategories()).thenReturn(Arrays.asList(testCategory));

        // When
        MessageResponse response = categoryService.getAllCategories();

        // Then
        assertTrue(response.isSuccess());
        assertNotNull(response.getData());
        verify(testDataService).generateTestCategories();
    }
} 