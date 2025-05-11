package com.sumativa1joelarias.demo.microservices.forums.service;

import com.sumativa1joelarias.demo.microservices.forums.model.Post;
import com.sumativa1joelarias.demo.microservices.forums.repository.PostRepository;
import com.sumativa1joelarias.demo.microservices.users.repository.UserRepository;
import com.sumativa1joelarias.demo.microservices.forums.repository.CategoryRepository;
import com.sumativa1joelarias.demo.microservices.forums.dto.MessageResponse;
import com.sumativa1joelarias.demo.microservices.forums.dto.CreatePostDTO;
import com.sumativa1joelarias.demo.microservices.forums.dto.PostDTO;
import com.sumativa1joelarias.demo.microservices.users.model.User;
import com.sumativa1joelarias.demo.microservices.users.enums.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.data.domain.PageImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class PostService {

    private static final Logger logger = LoggerFactory.getLogger(PostService.class);

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;
    
    @Autowired
    private PostTestDataService testDataService;
    
    // Variable para controlar si se deben usar datos de prueba siempre
    private boolean alwaysUseTestData = false;

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

    // Nuevo método para crear post usando DTO
    public MessageResponse createPostFromDTO(CreatePostDTO createPostDTO) {
        // Validar que el usuario existe y tiene permisos
        return userRepository.findById(createPostDTO.getUserId())
            .map(user -> {
                if (user.getStatus().equals("BANNED")) {
                    return MessageResponse.error("Usuario baneado no puede crear posts");
                }
                
                // Validar que la categoría existe
                if (!categoryRepository.existsById(createPostDTO.getCategoryId())) {
                    return MessageResponse.error("La categoría especificada no existe");
                }

                // Convertir DTO a entidad
                Post newPost = Post.builder()
                    .title(createPostDTO.getTitle())
                    .content(createPostDTO.getContent())
                    .userId(createPostDTO.getUserId())
                    .categoryId(createPostDTO.getCategoryId())
                    .status("ACTIVE")
                    .build();

                logger.info("Objeto Post ANTES de guardar (newPost): " + newPost.toString());

                Post savedPost = postRepository.save(newPost);

                logger.info("Objeto Post DESPUÉS de guardar (savedPost): " + savedPost.toString());
                
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
        
        // Si siempre queremos usar datos de prueba o no hay posts en la base de datos
        if (alwaysUseTestData || posts.isEmpty()) {
            logger.info("Usando datos de prueba para getAllPosts");
            posts = testDataService.generateTestPosts();
        }
        
        return MessageResponse.success("Posts obtenidos exitosamente", posts);
    }

    // Método para obtener posts paginados
    public MessageResponse getPagedPosts(int page, int size, String sortBy, String direction) {
        // Validar parámetros de paginación
        if (page < 0) page = 0;
        if (size <= 0) size = 10;
        if (size > 100) size = 100; // Limitar para evitar consultas muy grandes
        
        // Validar parámetros de ordenamiento
        if (sortBy == null || sortBy.isEmpty()) sortBy = "createdAt";
        
        // Crear objeto Pageable para la paginación y ordenamiento
        Sort.Direction sortDirection = "asc".equalsIgnoreCase(direction) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
        
        // Obtener página de posts
        Page<Post> postsPage = postRepository.findAll(pageable);
        
        // Si siempre queremos usar datos de prueba o no hay posts en la base de datos
        if (alwaysUseTestData || postsPage.isEmpty()) {
            logger.info("Usando datos de prueba para getPagedPosts");
            List<Post> testPosts = testDataService.generateTestPosts();
            
            // Ordenar según los parámetros
            if ("asc".equalsIgnoreCase(direction)) {
                if ("title".equals(sortBy)) {
                    testPosts.sort((a, b) -> a.getTitle().compareTo(b.getTitle()));
                } else if ("createdAt".equals(sortBy)) {
                    testPosts.sort((a, b) -> a.getCreatedAt().compareTo(b.getCreatedAt()));
                }
            } else {
                if ("title".equals(sortBy)) {
                    testPosts.sort((a, b) -> b.getTitle().compareTo(a.getTitle()));
                } else if ("createdAt".equals(sortBy)) {
                    testPosts.sort((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt()));
                }
            }
            
            // Crear paginación manual
            int start = (int) pageable.getOffset();
            int end = Math.min((start + pageable.getPageSize()), testPosts.size());
            
            // Si el inicio está fuera de rango, devolver lista vacía
            if (start >= testPosts.size()) {
                return MessageResponse.success("No hay más posts disponibles", 
                        new PageImpl<>(List.of(), pageable, testPosts.size()));
            }
            
            // Subconjunto de datos para la página actual
            List<Post> pageContent = testPosts.subList(start, end);
            Page<Post> testPage = new PageImpl<>(pageContent, pageable, testPosts.size());
            
            return MessageResponse.success("Posts de prueba obtenidos exitosamente", testPage);
        }
        
        return MessageResponse.success("Posts obtenidos exitosamente", postsPage);
    }

    // Método para buscar posts por título o contenido
    public MessageResponse searchPosts(String query) {
        if (query == null || query.trim().isEmpty()) {
            return MessageResponse.error("El término de búsqueda no puede estar vacío");
        }
        
        List<Post> posts = postRepository.findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(query, query);
        
        // Si siempre queremos usar datos de prueba o no hay resultados en la base de datos
        if (alwaysUseTestData || posts.isEmpty()) {
            logger.info("Usando datos de prueba para searchPosts");
            posts = testDataService.searchTestPosts(query);
        }
        
        return MessageResponse.success("Posts encontrados exitosamente", posts);
    }

    public MessageResponse getPostById(Long id) {
        Optional<Post> postOptional = postRepository.findById(id);
        
        // Si siempre queremos usar datos de prueba o no se encontró el post
        if (alwaysUseTestData || !postOptional.isPresent()) {
            logger.info("Usando datos de prueba para getPostById");
            Post testPost = testDataService.getTestPostById(id);
            
            if (testPost != null) {
                return MessageResponse.success("Post de prueba encontrado exitosamente", testPost);
            } else {
                return MessageResponse.error("Post no encontrado");
            }
        }
        
        return postOptional
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
        List<Post> posts = postRepository.findByCategoryId(categoryId);
        
        // Si siempre queremos usar datos de prueba o no hay posts en la base de datos
        if (alwaysUseTestData || posts.isEmpty()) {
            logger.info("Usando datos de prueba para getPostsByCategory");
            posts = testDataService.getTestPostsByCategory(categoryId);
        }
        
        if (posts.isEmpty()) {
            return MessageResponse.error("No se encontraron posts para la categoría especificada");
        }

        return MessageResponse.success("Posts obtenidos exitosamente", posts);
    }

    public MessageResponse getPostsByUser(Long userId) {
        List<Post> posts = postRepository.findByUserId(userId);
        
        // Si siempre queremos usar datos de prueba o no hay posts en la base de datos
        if (alwaysUseTestData || posts.isEmpty()) {
            logger.info("Usando datos de prueba para getPostsByUser");
            posts = testDataService.getTestPostsByUser(userId);
        }
        
        if (posts.isEmpty()) {
            return MessageResponse.error("No se encontraron posts para el usuario especificado");
        }

        return MessageResponse.success("Posts obtenidos exitosamente", posts);
    }

    private PostDTO convertToDTO(Post post) {
        // Este método se usaría para convertir la entidad Post a un DTO
        // Implementación depende de cómo quieras transformar tus datos
        return null; // Por ahora regresamos null
    }
} 