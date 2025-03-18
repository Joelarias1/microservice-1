package com.sumativa1joelarias.demo.microservices.forums.service;

import com.sumativa1joelarias.demo.microservices.forums.model.Post;
import com.sumativa1joelarias.demo.microservices.forums.repository.PostRepository;
import com.sumativa1joelarias.demo.microservices.users.repository.UserRepository;
import com.sumativa1joelarias.demo.microservices.forums.repository.CategoryRepository;
import com.sumativa1joelarias.demo.microservices.forums.dto.MessageResponse;
import com.sumativa1joelarias.demo.microservices.users.model.User;
import com.sumativa1joelarias.demo.microservices.users.enums.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private boolean canUserModifyPost(User user, Post post) {
        // Si el usuario está baneado, no puede hacer nada
        if (user.getStatus().equals("BANNED")) {
            return false;
        }

        // Si es ADMIN, puede hacer todo
        if (user.getRole() == UserRole.ADMIN) {
            return true;
        }

        // Si es el dueño del post, puede modificarlo
        if (post.getUserId().equals(user.getId())) {
            return true;
        }

        // Si es MODERATOR, solo puede moderar (cambiar status)
        return user.getRole() == UserRole.MODERATOR;
    }

    public MessageResponse createPost(Post post) {
        // Validar que el usuario existe y tiene permisos
        return userRepository.findById(post.getUserId())
            .map(user -> {
                if (user.getStatus().equals("BANNED")) {
                    return MessageResponse.error("Usuario baneado no puede crear posts");
                }
                
                // Validar que la categoría existe
                if (!categoryRepository.existsById(post.getCategoryId())) {
                    return MessageResponse.error("La categoría especificada no existe");
                }

                Post savedPost = postRepository.save(post);
                return MessageResponse.success("Post creado exitosamente", savedPost);
            })
            .orElse(MessageResponse.error("El usuario especificado no existe"));
    }

    public MessageResponse updatePost(Long id, Post postDetails) {
        return postRepository.findById(id)
                .map(post -> {
                    // Obtener el usuario que intenta editar
                    Optional<User> userOptional = userRepository.findById(postDetails.getUserId());
                    if (!userOptional.isPresent()) {
                        return MessageResponse.error("Usuario no encontrado");
                    }

                    User user = userOptional.get();

                    // Si el usuario está baneado
                    if (user.getStatus().equals("BANNED")) {
                        return MessageResponse.error("Usuario baneado no puede realizar esta acción");
                    }

                    // Verificar permisos según el rol
                    if (user.getRole() == UserRole.ADMIN) {
                        // El admin puede editar todo
                        updatePostFields(post, postDetails);
                        Post updatedPost = postRepository.save(post);
                        return MessageResponse.success("Post actualizado exitosamente por admin", updatedPost);
                    } else if (post.getUserId().equals(user.getId())) {
                        // El dueño puede editar su propio post
                        updatePostFields(post, postDetails);
                        Post updatedPost = postRepository.save(post);
                        return MessageResponse.success("Post actualizado exitosamente por el dueño", updatedPost);
                    } else if (user.getRole() == UserRole.MODERATOR) {
                        // El moderador solo puede cambiar el status
                        post.setStatus(postDetails.getStatus());
                        Post updatedPost = postRepository.save(post);
                        return MessageResponse.success("Status del post actualizado exitosamente por moderador", updatedPost);
                    } else {
                        return MessageResponse.error("No tienes permiso para editar este post");
                    }
                })
                .orElse(MessageResponse.error("Post no encontrado"));
    }

    private void updatePostFields(Post post, Post postDetails) {
        post.setTitle(postDetails.getTitle());
        post.setContent(postDetails.getContent());
        post.setStatus(postDetails.getStatus());
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
        // Verificar si el post existe
        Optional<Post> postOptional = postRepository.findById(id);
        if (!postOptional.isPresent()) {
            return MessageResponse.error("Post no encontrado");
        }
        
        Post post = postOptional.get();
        
        // Verificar si el usuario existe
        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()) {
            return MessageResponse.error("Usuario no encontrado");
        }
        
        User user = userOptional.get();
        
        // Si el usuario está baneado
        if (user.getStatus().equals("BANNED")) {
            return MessageResponse.error("Usuario baneado no puede realizar esta acción");
        }
        
        // Si el post pertenece a otro usuario y el solicitante no es ADMIN o MODERATOR
        Long postOwnerId = post.getUserId();
        boolean isOwner = postOwnerId.equals(userId);
        boolean isAdmin = user.getRole() == UserRole.ADMIN;
        boolean isModerator = user.getRole() == UserRole.MODERATOR;
        
        if (!isOwner && !isAdmin && !isModerator) {
            return MessageResponse.error("No tienes permiso para eliminar este post. Solo el dueño, moderadores o administradores pueden eliminarlo.");
        }
        
        // Si llegamos aquí, el usuario tiene permiso para eliminar
        try {
            postRepository.delete(post);
            
            if (isAdmin) {
                return MessageResponse.success("Post eliminado exitosamente por admin");
            } else if (isModerator) {
                return MessageResponse.success("Post eliminado exitosamente por moderador");
            } else {
                return MessageResponse.success("Post eliminado exitosamente por el dueño");
            }
        } catch (Exception e) {
            return MessageResponse.error("Error al eliminar el post: " + e.getMessage());
        }
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