package com.sumativa1joelarias.demo.microservices.forums.controller;

import com.sumativa1joelarias.demo.microservices.forums.model.Category;
import com.sumativa1joelarias.demo.microservices.forums.service.CategoryService;
import com.sumativa1joelarias.demo.microservices.forums.dto.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<MessageResponse> getAllCategories() {
        MessageResponse response = categoryService.getAllCategories();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<MessageResponse> createCategory(@RequestBody Category category) {
        MessageResponse response = categoryService.createCategory(category);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageResponse> getCategoryById(@PathVariable Long id) {
        MessageResponse response = categoryService.getCategoryById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageResponse> updateCategory(@PathVariable Long id, @RequestBody Category categoryDetails) {
        MessageResponse response = categoryService.updateCategory(id, categoryDetails);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteCategory(@PathVariable Long id) {
        MessageResponse response = categoryService.deleteCategory(id);
        return ResponseEntity.ok(response);
    }
} 