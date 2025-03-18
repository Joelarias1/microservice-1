package com.sumativa1joelarias.demo.microservices.forums.controller;

import com.sumativa1joelarias.demo.microservices.forums.model.Post;
import com.sumativa1joelarias.demo.microservices.forums.repository.PostRepository;
import com.sumativa1joelarias.demo.microservices.forums.dto.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping
    public ResponseEntity<MessageResponse> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return ResponseEntity.ok(MessageResponse.success("Publicaciones obtenidas exitosamente", posts));
    }

    @PostMapping
    public ResponseEntity<MessageResponse> createPost(@RequestBody Post post) {
        Post savedPost = postRepository.save(post);
        return ResponseEntity.ok(MessageResponse.success("Publicación creada exitosamente", savedPost));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageResponse> getPostById(@PathVariable Long id) {
        return postRepository.findById(id)
                .map(post -> ResponseEntity.ok(MessageResponse.success("Publicación encontrada exitosamente", post)))
                .orElse(ResponseEntity.ok(MessageResponse.error("Publicación no encontrada")));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageResponse> updatePost(@PathVariable Long id, @RequestBody Post postDetails) {
        return postRepository.findById(id)
                .map(post -> {
                    post.setTitle(postDetails.getTitle());
                    post.setContent(postDetails.getContent());
                    post.setStatus(postDetails.getStatus());
                    Post updatedPost = postRepository.save(post);
                    return ResponseEntity.ok(MessageResponse.success("Publicación actualizada exitosamente", updatedPost));
                })
                .orElse(ResponseEntity.ok(MessageResponse.error("Publicación no encontrada")));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deletePost(@PathVariable Long id) {
        return postRepository.findById(id)
                .map(post -> {
                    postRepository.delete(post);
                    return ResponseEntity.ok(MessageResponse.success("Publicación eliminada exitosamente"));
                })
                .orElse(ResponseEntity.ok(MessageResponse.error("Publicación no encontrada")));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<MessageResponse> getPostsByCategory(@PathVariable Long categoryId) {
        List<Post> posts = postRepository.findByCategoryId(categoryId);
        return ResponseEntity.ok(MessageResponse.success("Publicaciones por categoría obtenidas exitosamente", posts));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<MessageResponse> getPostsByUser(@PathVariable Long userId) {
        List<Post> posts = postRepository.findByUserId(userId);
        return ResponseEntity.ok(MessageResponse.success("Publicaciones del usuario obtenidas exitosamente", posts));
    }
} 