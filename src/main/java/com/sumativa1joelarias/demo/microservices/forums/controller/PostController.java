package com.sumativa1joelarias.demo.microservices.forums.controller;

import com.sumativa1joelarias.demo.microservices.forums.model.Post;
import com.sumativa1joelarias.demo.microservices.forums.service.PostService;
import com.sumativa1joelarias.demo.microservices.forums.dto.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping
    public ResponseEntity<MessageResponse> getAllPosts() {
        MessageResponse response = postService.getAllPosts();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<MessageResponse> createPost(@RequestBody Post post) {
        MessageResponse response = postService.createPost(post);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageResponse> getPostById(@PathVariable Long id) {
        MessageResponse response = postService.getPostById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageResponse> updatePost(@PathVariable Long id, @RequestBody Post postDetails) {
        MessageResponse response = postService.updatePost(id, postDetails);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deletePost(
            @PathVariable Long id,
            @RequestParam(required = true) Long userId) {
        MessageResponse response = postService.deletePost(id, userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<MessageResponse> getPostsByCategory(@PathVariable Long categoryId) {
        MessageResponse response = postService.getPostsByCategory(categoryId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<MessageResponse> getPostsByUser(@PathVariable Long userId) {
        MessageResponse response = postService.getPostsByUser(userId);
        return ResponseEntity.ok(response);
    }
} 