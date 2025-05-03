package com.sumativa1joelarias.demo.microservices.forums.controller;

import com.sumativa1joelarias.demo.microservices.forums.model.Comment;
import com.sumativa1joelarias.demo.microservices.forums.service.CommentService;
import com.sumativa1joelarias.demo.microservices.forums.dto.CreateCommentDTO;
import com.sumativa1joelarias.demo.microservices.forums.dto.MessageResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping
    public ResponseEntity<MessageResponse> createComment(@Valid @RequestBody CreateCommentDTO createCommentDTO) {
        MessageResponse response = commentService.createComment(createCommentDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<MessageResponse> getCommentsByPost(@PathVariable Long postId) {
        MessageResponse response = commentService.getAllCommentsByPost(postId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageResponse> getCommentById(@PathVariable Long id) {
        MessageResponse response = commentService.getCommentById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageResponse> updateComment(
            @PathVariable Long id,
            @RequestParam String content,
            @RequestParam Long userId) {
        MessageResponse response = commentService.updateComment(id, content, userId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteComment(
            @PathVariable Long id,
            @RequestParam Long userId) {
        MessageResponse response = commentService.deleteComment(id, userId);
        return ResponseEntity.ok(response);
    }
} 