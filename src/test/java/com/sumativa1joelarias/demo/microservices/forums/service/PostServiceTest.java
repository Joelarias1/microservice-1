package com.sumativa1joelarias.demo.microservices.forums.service;

import com.sumativa1joelarias.demo.microservices.forums.dto.CreatePostDTO;
import com.sumativa1joelarias.demo.microservices.forums.dto.MessageResponse;
import com.sumativa1joelarias.demo.microservices.forums.model.Post;
import com.sumativa1joelarias.demo.microservices.forums.repository.CategoryRepository;
import com.sumativa1joelarias.demo.microservices.forums.repository.PostRepository;
import com.sumativa1joelarias.demo.microservices.users.enums.UserRole;
import com.sumativa1joelarias.demo.microservices.users.model.User;
import com.sumativa1joelarias.demo.microservices.users.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private PostTestDataService testDataService;

    @InjectMocks
    private PostService postService;

    private User testUser;
    private Post testPost;
    private CreatePostDTO createPostDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testUser");
        testUser.setStatus("ACTIVE");
        testUser.setRole(UserRole.USER);

        testPost = Post.builder()
                .id(1L)
                .title("Test Post")
                .content("Test Content")
                .userId(1L)
                .categoryId(1L)
                .status("ACTIVE")
                .createdAt(LocalDateTime.now())
                .build();

        createPostDTO = new CreatePostDTO();
        createPostDTO.setTitle("Test Post");
        createPostDTO.setContent("Test Content");
        createPostDTO.setUserId(1L);
        createPostDTO.setCategoryId(1L);
    }

    @Test
    void createPost_Success() {
        // Given
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(categoryRepository.existsById(1L)).thenReturn(true);
        when(postRepository.save(any(Post.class))).thenReturn(testPost);

        // When
        MessageResponse response = postService.createPost(testPost);

        // Then
        assertTrue(response.isSuccess());
        assertNotNull(response.getData());
        verify(postRepository).save(any(Post.class));
    }

    @Test
    void createPost_UserBanned() {
        // Given
        testUser.setStatus("BANNED");
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        // When
        MessageResponse response = postService.createPost(testPost);

        // Then
        assertFalse(response.isSuccess());
        assertEquals("Usuario baneado no puede crear posts", response.getMessage());
        verify(postRepository, never()).save(any(Post.class));
    }

    @Test
    void createPostFromDTO_Success() {
        // Given
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(categoryRepository.existsById(1L)).thenReturn(true);
        when(postRepository.save(any(Post.class))).thenReturn(testPost);

        // When
        MessageResponse response = postService.createPostFromDTO(createPostDTO);

        // Then
        assertTrue(response.isSuccess());
        assertNotNull(response.getData());
        verify(postRepository).save(any(Post.class));
    }

    @Test
    void updatePost_AsAdmin() {
        // Given
        User adminUser = new User();
        adminUser.setId(2L);
        adminUser.setRole(UserRole.ADMIN);
        adminUser.setStatus("ACTIVE");

        Post updatePost = Post.builder()
                .id(1L)
                .title("Updated Title")
                .content("Updated Content")
                .userId(2L)
                .status("ACTIVE")
                .build();

        when(postRepository.findById(1L)).thenReturn(Optional.of(testPost));
        when(userRepository.findById(2L)).thenReturn(Optional.of(adminUser));
        when(postRepository.save(any(Post.class))).thenReturn(updatePost);

        // When
        MessageResponse response = postService.updatePost(1L, updatePost);

        // Then
        assertTrue(response.isSuccess());
        assertTrue(response.getMessage().contains("admin"));
        verify(postRepository).save(any(Post.class));
    }

    @Test
    void updatePost_AsModerator() {
        // Given
        User moderatorUser = new User();
        moderatorUser.setId(2L);
        moderatorUser.setRole(UserRole.MODERATOR);
        moderatorUser.setStatus("ACTIVE");

        Post updatePost = Post.builder()
                .id(1L)
                .userId(2L)
                .status("INACTIVE")
                .build();

        when(postRepository.findById(1L)).thenReturn(Optional.of(testPost));
        when(userRepository.findById(2L)).thenReturn(Optional.of(moderatorUser));
        when(postRepository.save(any(Post.class))).thenReturn(updatePost);

        // When
        MessageResponse response = postService.updatePost(1L, updatePost);

        // Then
        assertTrue(response.isSuccess());
        assertTrue(response.getMessage().contains("moderador"));
        verify(postRepository).save(any(Post.class));
    }

    @Test
    void getPagedPosts_Success() {
        // Given
        List<Post> posts = Arrays.asList(testPost);
        Page<Post> page = new PageImpl<>(posts);
        when(postRepository.findAll(any(Pageable.class))).thenReturn(page);

        // When
        MessageResponse response = postService.getPagedPosts(0, 10, "createdAt", "desc");

        // Then
        assertTrue(response.isSuccess());
        assertNotNull(response.getData());
        verify(postRepository).findAll(any(Pageable.class));
    }

    @Test
    void searchPosts_Success() {
        // Given
        String query = "test";
        when(postRepository.findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(query, query))
                .thenReturn(Arrays.asList(testPost));

        // When
        MessageResponse response = postService.searchPosts(query);

        // Then
        assertTrue(response.isSuccess());
        assertNotNull(response.getData());
        verify(postRepository).findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(query, query);
    }

    @Test
    void searchPosts_EmptyQuery() {
        // When
        MessageResponse response = postService.searchPosts("");

        // Then
        assertFalse(response.isSuccess());
        assertEquals("El término de búsqueda no puede estar vacío", response.getMessage());
        verify(postRepository, never())
                .findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(anyString(), anyString());
    }

    @Test
    void getPostsByCategory_Success() {
        // Given
        when(postRepository.findByCategoryId(1L)).thenReturn(Arrays.asList(testPost));
        when(testDataService.getTestPostsByCategory(1L)).thenReturn(Arrays.asList(testPost));

        // When
        MessageResponse response = postService.getPostsByCategory(1L);

        // Then
        assertTrue(response.isSuccess());
        assertNotNull(response.getData());
        verify(postRepository).findByCategoryId(1L);
    }

    @Test
    void getPostsByUser_Success() {
        // Given
        when(postRepository.findByUserId(1L)).thenReturn(Arrays.asList(testPost));
        when(testDataService.getTestPostsByUser(1L)).thenReturn(Arrays.asList(testPost));

        // When
        MessageResponse response = postService.getPostsByUser(1L);

        // Then
        assertTrue(response.isSuccess());
        assertNotNull(response.getData());
        verify(postRepository).findByUserId(1L);
    }
} 