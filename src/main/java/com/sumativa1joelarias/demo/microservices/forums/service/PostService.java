package com.sumativa1joelarias.demo.microservices.forums.service;

import com.sumativa1joelarias.demo.microservices.forums.model.Post;
import com.sumativa1joelarias.demo.microservices.forums.repository.PostRepository;
import com.sumativa1joelarias.demo.microservices.users.repository.UserRepository;
import com.sumativa1joelarias.demo.microservices.forums.repository.CategoryRepository;
import com.sumativa1joelarias.demo.microservices.forums.dto.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public MessageResponse createPost(Post post) {
        // Validar que el usuario existe
        if (!userRepository.existsById(post.getUserId())) {
            return MessageResponse.error("El usuario especificado no existe");
        }

        // Validar que la categoría existe
        if (!categoryRepository.existsById(post.getCategoryId())) {
            return MessageResponse.error("La categoría especificada no existe");
        }

        Post savedPost = postRepository.save(post);
        return MessageResponse.success("Post creado exitosamente", savedPost);
    }

    public MessageResponse updatePost(Long id, Post postDetails) {
        return postRepository.findById(id)
                .map(post -> {
                    // Validar que el usuario tiene permiso para editar este post
                    if (!post.getUserId().equals(postDetails.getUserId())) {
                        return MessageResponse.error("No tienes permiso para editar este post");
                    }

                    post.setTitle(postDetails.getTitle());
                    post.setContent(postDetails.getContent());
                    post.setStatus(postDetails.getStatus());
                    
                    Post updatedPost = postRepository.save(post);
                    return MessageResponse.success("Post actualizado exitosamente", updatedPost);
                })
                .orElse(MessageResponse.error("Post no encontrado"));
    }

    public MessageResponse getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return MessageResponse.success("Posts obtenidos exitosamente", posts);
    }

    public MessageResponse getPostById(Long id) {
        return postRepository.findById(id)
                .map(post -> MessageResponse.success("Post encontrado exitosamente", post))
                .orElse(MessageResponse.error("Post no encontrado"));
    }

    public MessageResponse deletePost(Long id, Long userId) {
        return postRepository.findById(id)
                .map(post -> {
                    // Validar que el usuario tiene permiso para eliminar este post
                    if (!post.getUserId().equals(userId)) {
                        return MessageResponse.error("No tienes permiso para eliminar este post");
                    }
                    
                    postRepository.delete(post);
                    return MessageResponse.success("Post eliminado exitosamente");
                })
                .orElse(MessageResponse.error("Post no encontrado"));
    }

    public MessageResponse getPostsByCategory(Long categoryId) {
        // Validar que la categoría existe
        if (!categoryRepository.existsById(categoryId)) {
            return MessageResponse.error("La categoría especificada no existe");
        }

        List<Post> posts = postRepository.findByCategoryId(categoryId);
        return MessageResponse.success("Posts obtenidos exitosamente", posts);
    }

    public MessageResponse getPostsByUser(Long userId) {
        // Validar que el usuario existe
        if (!userRepository.existsById(userId)) {
            return MessageResponse.error("El usuario especificado no existe");
        }

        List<Post> posts = postRepository.findByUserId(userId);
        return MessageResponse.success("Posts obtenidos exitosamente", posts);
    }
} 