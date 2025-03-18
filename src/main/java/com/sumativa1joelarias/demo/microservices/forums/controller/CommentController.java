package com.sumativa1joelarias.demo.microservices.forums.controller;

import com.sumativa1joelarias.demo.microservices.forums.model.Comment;
import com.sumativa1joelarias.demo.microservices.forums.repository.CommentRepository;
import com.sumativa1joelarias.demo.microservices.forums.dto.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @PostMapping
    public ResponseEntity<MessageResponse> createComment(@RequestBody Comment comment) {
        Comment savedComment = commentRepository.save(comment);
        return ResponseEntity.ok(MessageResponse.success("Comentario creado exitosamente", savedComment));
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<MessageResponse> getCommentsByPost(@PathVariable Long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        return ResponseEntity.ok(MessageResponse.success("Comentarios obtenidos exitosamente", comments));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<MessageResponse> getCommentsByUser(@PathVariable Long userId) {
        List<Comment> comments = commentRepository.findByUserId(userId);
        return ResponseEntity.ok(MessageResponse.success("Comentarios del usuario obtenidos exitosamente", comments));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageResponse> updateComment(@PathVariable Long id, @RequestBody Comment commentDetails) {
        return commentRepository.findById(id)
                .map(comment -> {
                    comment.setContent(commentDetails.getContent());
                    comment.setStatus(commentDetails.getStatus());
                    Comment updatedComment = commentRepository.save(comment);
                    return ResponseEntity.ok(MessageResponse.success("Comentario actualizado exitosamente", updatedComment));
                })
                .orElse(ResponseEntity.ok(MessageResponse.error("Comentario no encontrado")));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteComment(@PathVariable Long id) {
        return commentRepository.findById(id)
                .map(comment -> {
                    commentRepository.delete(comment);
                    return ResponseEntity.ok(MessageResponse.success("Comentario eliminado exitosamente"));
                })
                .orElse(ResponseEntity.ok(MessageResponse.error("Comentario no encontrado")));
    }
} 