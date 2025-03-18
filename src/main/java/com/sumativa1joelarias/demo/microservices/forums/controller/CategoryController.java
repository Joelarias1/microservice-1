package com.sumativa1joelarias.demo.microservices.forums.controller;

import com.sumativa1joelarias.demo.microservices.forums.model.Category;
import com.sumativa1joelarias.demo.microservices.forums.repository.CategoryRepository;
import com.sumativa1joelarias.demo.microservices.forums.dto.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping
    public ResponseEntity<MessageResponse> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return ResponseEntity.ok(MessageResponse.success("Categorías obtenidas exitosamente", categories));
    }

    @PostMapping
    public ResponseEntity<MessageResponse> createCategory(@RequestBody Category category) {
        Category savedCategory = categoryRepository.save(category);
        return ResponseEntity.ok(MessageResponse.success("Categoría creada exitosamente", savedCategory));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageResponse> getCategoryById(@PathVariable Long id) {
        return categoryRepository.findById(id)
                .map(category -> ResponseEntity.ok(MessageResponse.success("Categoría encontrada exitosamente", category)))
                .orElse(ResponseEntity.ok(MessageResponse.error("Categoría no encontrada")));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageResponse> updateCategory(@PathVariable Long id, @RequestBody Category categoryDetails) {
        return categoryRepository.findById(id)
                .map(category -> {
                    category.setName(categoryDetails.getName());
                    category.setDescription(categoryDetails.getDescription());
                    Category updatedCategory = categoryRepository.save(category);
                    return ResponseEntity.ok(MessageResponse.success("Categoría actualizada exitosamente", updatedCategory));
                })
                .orElse(ResponseEntity.ok(MessageResponse.error("Categoría no encontrada")));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteCategory(@PathVariable Long id) {
        return categoryRepository.findById(id)
                .map(category -> {
                    categoryRepository.delete(category);
                    return ResponseEntity.ok(MessageResponse.success("Categoría eliminada exitosamente"));
                })
                .orElse(ResponseEntity.ok(MessageResponse.error("Categoría no encontrada")));
    }
} 