package com.sumativa1joelarias.demo.microservices.forums.service;

import com.sumativa1joelarias.demo.microservices.forums.dto.CommentDTO;
import com.sumativa1joelarias.demo.microservices.forums.dto.CreateCommentDTO;
import com.sumativa1joelarias.demo.microservices.forums.dto.MessageResponse;
import com.sumativa1joelarias.demo.microservices.forums.model.Comment;
import com.sumativa1joelarias.demo.microservices.forums.model.Post;
import com.sumativa1joelarias.demo.microservices.forums.repository.CommentRepository;
import com.sumativa1joelarias.demo.microservices.forums.repository.PostRepository;
import com.sumativa1joelarias.demo.microservices.users.enums.UserRole;
import com.sumativa1joelarias.demo.microservices.users.model.User;
import com.sumativa1joelarias.demo.microservices.users.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CommentService commentService;

    private User testUser;
    private Post testPost;
    private Comment testComment;
    private CreateCommentDTO createCommentDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testUser");
        testUser.setStatus("ACTIVE");
        testUser.setRole(UserRole.USER);

        testPost = new Post();
        testPost.setId(1L);

        testComment = Comment.builder()
                .id(1L)
                .content("Test comment")
                .user(testUser)
                .post(testPost)
                .status("ACTIVE")
                .build();

        createCommentDTO = new CreateCommentDTO();
        createCommentDTO.setContent("Test comment");
        createCommentDTO.setUserId(1L);
        createCommentDTO.setPostId(1L);
    }

    @Test
    void createComment_Success() {
        // Given
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(postRepository.findById(1L)).thenReturn(Optional.of(testPost));
        when(commentRepository.save(any(Comment.class))).thenReturn(testComment);

        // When
        MessageResponse response = commentService.createComment(createCommentDTO);

        // Then
        assertTrue(response.isSuccess());
        assertNotNull(response.getData());
        verify(commentRepository).save(any(Comment.class));
    }

    @Test
    void createComment_UserNotFound() {
        // Given
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // When
        MessageResponse response = commentService.createComment(createCommentDTO);

        // Then
        assertFalse(response.isSuccess());
        assertEquals("Usuario no encontrado", response.getMessage());
        verify(commentRepository, never()).save(any(Comment.class));
    }

    @Test
    void createComment_UserBanned() {
        // Given
        testUser.setStatus("BANNED");
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        // When
        MessageResponse response = commentService.createComment(createCommentDTO);

        // Then
        assertFalse(response.isSuccess());
        assertEquals("Usuario baneado no puede comentar", response.getMessage());
        verify(commentRepository, never()).save(any(Comment.class));
    }

    @Test
    void deleteComment_AsAdmin() {
        // Given
        User adminUser = new User();
        adminUser.setId(2L);
        adminUser.setRole(UserRole.ADMIN);
        adminUser.setStatus("ACTIVE");

        when(commentRepository.findById(1L)).thenReturn(Optional.of(testComment));
        when(userRepository.findById(2L)).thenReturn(Optional.of(adminUser));

        // When
        MessageResponse response = commentService.deleteComment(1L, 2L);

        // Then
        assertTrue(response.isSuccess());
        assertEquals("Comentario eliminado por admin", response.getMessage());
        verify(commentRepository).delete(testComment);
    }

    @Test
    void updateComment_AsOwner() {
        // Given
        String newContent = "Updated content";
        when(commentRepository.findById(1L)).thenReturn(Optional.of(testComment));
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(commentRepository.save(any(Comment.class))).thenReturn(testComment);

        // When
        MessageResponse response = commentService.updateComment(1L, newContent, 1L);

        // Then
        assertTrue(response.isSuccess());
        assertEquals("Comentario actualizado exitosamente", response.getMessage());
        verify(commentRepository).save(any(Comment.class));
    }

    @Test
    void getAllCommentsByPost_Success() {
        // Given
        when(postRepository.existsById(1L)).thenReturn(true);
        when(commentRepository.findByPostId(1L)).thenReturn(Arrays.asList(testComment));

        // When
        MessageResponse response = commentService.getAllCommentsByPost(1L);

        // Then
        assertTrue(response.isSuccess());
        assertNotNull(response.getData());
        verify(commentRepository).findByPostId(1L);
    }
} 