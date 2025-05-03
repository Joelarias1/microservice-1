package com.sumativa1joelarias.demo.microservices.forums.service;

import com.sumativa1joelarias.demo.microservices.forums.model.Comment;
import com.sumativa1joelarias.demo.microservices.forums.model.Post;
import com.sumativa1joelarias.demo.microservices.forums.repository.CommentRepository;
import com.sumativa1joelarias.demo.microservices.forums.repository.PostRepository;
import com.sumativa1joelarias.demo.microservices.users.repository.UserRepository;
import com.sumativa1joelarias.demo.microservices.forums.dto.CommentDTO;
import com.sumativa1joelarias.demo.microservices.forums.dto.CreateCommentDTO;
import com.sumativa1joelarias.demo.microservices.forums.dto.MessageResponse;
import com.sumativa1joelarias.demo.microservices.users.model.User;
import com.sumativa1joelarias.demo.microservices.users.enums.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public MessageResponse createComment(CreateCommentDTO createCommentDTO) {
        // Verificar que el usuario existe y no está baneado
        Optional<User> userOptional = userRepository.findById(createCommentDTO.getUserId());
        if (!userOptional.isPresent()) {
            return MessageResponse.error("Usuario no encontrado");
        }
        
        User user = userOptional.get();
        if (user.getStatus().equals("BANNED")) {
            return MessageResponse.error("Usuario baneado no puede comentar");
        }
        
        // Verificar que el post existe
        Optional<Post> postOptional = postRepository.findById(createCommentDTO.getPostId());
        if (!postOptional.isPresent()) {
            return MessageResponse.error("Post no encontrado");
        }
        
        // Crear y guardar el comentario
        Comment comment = Comment.builder()
            .content(createCommentDTO.getContent())
            .post(postOptional.get())
            .user(user)
            .status("ACTIVE")
            .build();
        
        Comment savedComment = commentRepository.save(comment);
        
        // Convertir a DTO para la respuesta
        CommentDTO commentDTO = convertToDTO(savedComment);
        
        return MessageResponse.success("Comentario creado exitosamente", commentDTO);
    }

    public MessageResponse getAllCommentsByPost(Long postId) {
        // Verificar que el post existe
        if (!postRepository.existsById(postId)) {
            return MessageResponse.error("Post no encontrado");
        }
        
        List<Comment> comments = commentRepository.findByPostId(postId);
        
        // Convertir a DTOs
        List<CommentDTO> commentDTOs = comments.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
        
        return MessageResponse.success("Comentarios obtenidos exitosamente", commentDTOs);
    }

    public MessageResponse getCommentById(Long id) {
        return commentRepository.findById(id)
            .map(comment -> {
                CommentDTO commentDTO = convertToDTO(comment);
                return MessageResponse.success("Comentario encontrado exitosamente", commentDTO);
            })
            .orElse(MessageResponse.error("Comentario no encontrado"));
    }

    public MessageResponse updateComment(Long id, String content, Long userId) {
        // Verificar que el comentario existe
        Optional<Comment> commentOptional = commentRepository.findById(id);
        if (!commentOptional.isPresent()) {
            return MessageResponse.error("Comentario no encontrado");
        }
        
        Comment comment = commentOptional.get();
        
        // Verificar que el usuario existe
        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()) {
            return MessageResponse.error("Usuario no encontrado");
        }
        
        User user = userOptional.get();
        
        // Si el usuario está baneado
        if (user.getStatus().equals("BANNED")) {
            return MessageResponse.error("Usuario baneado no puede realizar esta acción");
        }
        
        boolean isOwner = comment.getUser().getId().equals(userId);
        boolean isAdmin = user.getRole() == UserRole.ADMIN;
        boolean isModerator = user.getRole() == UserRole.MODERATOR;
        
        // Solo el dueño del comentario, admin o moderador puede actualizar
        if (!isOwner && !isAdmin && !isModerator) {
            return MessageResponse.error("No tienes permiso para editar este comentario");
        }
        
        // Actualizar contenido
        comment.setContent(content);
        Comment updatedComment = commentRepository.save(comment);
        
        CommentDTO commentDTO = convertToDTO(updatedComment);
        
        if (isAdmin) {
            return MessageResponse.success("Comentario actualizado por admin", commentDTO);
        } else if (isModerator) {
            return MessageResponse.success("Comentario actualizado por moderador", commentDTO);
        } else {
            return MessageResponse.success("Comentario actualizado exitosamente", commentDTO);
        }
    }

    public MessageResponse deleteComment(Long id, Long userId) {
        // Verificar que el comentario existe
        Optional<Comment> commentOptional = commentRepository.findById(id);
        if (!commentOptional.isPresent()) {
            return MessageResponse.error("Comentario no encontrado");
        }
        
        Comment comment = commentOptional.get();
        
        // Verificar que el usuario existe
        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()) {
            return MessageResponse.error("Usuario no encontrado");
        }
        
        User user = userOptional.get();
        
        // Si el usuario está baneado
        if (user.getStatus().equals("BANNED")) {
            return MessageResponse.error("Usuario baneado no puede realizar esta acción");
        }
        
        boolean isOwner = comment.getUser().getId().equals(userId);
        boolean isAdmin = user.getRole() == UserRole.ADMIN;
        boolean isModerator = user.getRole() == UserRole.MODERATOR;
        
        // Solo el dueño, admin o moderador puede eliminar
        if (!isOwner && !isAdmin && !isModerator) {
            return MessageResponse.error("No tienes permiso para eliminar este comentario");
        }
        
        try {
            commentRepository.delete(comment);
            
            if (isAdmin) {
                return MessageResponse.success("Comentario eliminado por admin");
            } else if (isModerator) {
                return MessageResponse.success("Comentario eliminado por moderador");
            } else {
                return MessageResponse.success("Comentario eliminado exitosamente");
            }
        } catch (Exception e) {
            return MessageResponse.error("Error al eliminar el comentario: " + e.getMessage());
        }
    }

    private CommentDTO convertToDTO(Comment comment) {
        return CommentDTO.builder()
            .id(comment.getId())
            .content(comment.getContent())
            .postId(comment.getPost().getId())
            .userId(comment.getUser().getId())
            .username(comment.getUser().getUsername())
            .status(comment.getStatus())
            .createdAt(comment.getCreatedAt())
            .build();
    }
} 