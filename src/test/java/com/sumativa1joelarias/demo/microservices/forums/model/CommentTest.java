package com.sumativa1joelarias.demo.microservices.forums.model;

import com.sumativa1joelarias.demo.microservices.users.model.User;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CommentTest {

    @Test
    void builder_shouldCreateCommentWithAllFields() {
        // Given
        Long id = 1L;
        String content = "Test comment";
        Post post = new Post();
        User user = new User();
        LocalDateTime createdAt = LocalDateTime.now();
        String status = "ACTIVE";

        // When
        Comment comment = Comment.builder()
                .id(id)
                .content(content)
                .post(post)
                .user(user)
                .createdAt(createdAt)
                .status(status)
                .build();

        // Then
        assertEquals(id, comment.getId());
        assertEquals(content, comment.getContent());
        assertEquals(post, comment.getPost());
        assertEquals(user, comment.getUser());
        assertEquals(createdAt, comment.getCreatedAt());
        assertEquals(status, comment.getStatus());
    }

    @Test
    void noArgsConstructor_shouldCreateEmptyComment() {
        // When
        Comment comment = new Comment();

        // Then
        assertNull(comment.getId());
        assertNull(comment.getContent());
        assertNull(comment.getPost());
        assertNull(comment.getUser());
        assertNull(comment.getCreatedAt());
        assertEquals("ACTIVE", comment.getStatus());
    }

    @Test
    void allArgsConstructor_shouldCreateCommentWithAllFields() {
        // Given
        Long id = 1L;
        String content = "Test comment";
        Post post = new Post();
        User user = new User();
        LocalDateTime createdAt = LocalDateTime.now();
        String status = "ACTIVE";

        // When
        Comment comment = new Comment(id, content, post, user, createdAt, status);

        // Then
        assertEquals(id, comment.getId());
        assertEquals(content, comment.getContent());
        assertEquals(post, comment.getPost());
        assertEquals(user, comment.getUser());
        assertEquals(createdAt, comment.getCreatedAt());
        assertEquals(status, comment.getStatus());
    }

    @Test
    void settersAndGetters_shouldWorkCorrectly() {
        // Given
        Comment comment = new Comment();
        Long id = 1L;
        String content = "Test comment";
        Post post = new Post();
        User user = new User();
        LocalDateTime createdAt = LocalDateTime.now();
        String status = "INACTIVE";

        // When
        comment.setId(id);
        comment.setContent(content);
        comment.setPost(post);
        comment.setUser(user);
        comment.setCreatedAt(createdAt);
        comment.setStatus(status);

        // Then
        assertEquals(id, comment.getId());
        assertEquals(content, comment.getContent());
        assertEquals(post, comment.getPost());
        assertEquals(user, comment.getUser());
        assertEquals(createdAt, comment.getCreatedAt());
        assertEquals(status, comment.getStatus());
    }

    @Test
    void onCreate_shouldSetCreatedAtToCurrentTime() {
        // Given
        Comment comment = new Comment();
        LocalDateTime before = LocalDateTime.now();

        // When
        comment.onCreate();
        LocalDateTime after = LocalDateTime.now();

        // Then
        assertNotNull(comment.getCreatedAt());
        assertTrue(comment.getCreatedAt().isAfter(before) || comment.getCreatedAt().equals(before));
        assertTrue(comment.getCreatedAt().isBefore(after) || comment.getCreatedAt().equals(after));
    }

    @Test
    void defaultStatus_shouldBeActive() {
        // When
        Comment comment = new Comment();

        // Then
        assertEquals("ACTIVE", comment.getStatus());
    }

    @Test
    void equals_shouldWorkCorrectly() {
        // Given
        Comment comment1 = Comment.builder().id(1L).content("Test").build();
        Comment comment2 = Comment.builder().id(1L).content("Test").build();
        Comment comment3 = Comment.builder().id(2L).content("Test").build();

        // Then
        assertEquals(comment1, comment2);
        assertNotEquals(comment1, comment3);
    }

    @Test
    void hashCode_shouldBeConsistentWithEquals() {
        // Given
        Comment comment1 = Comment.builder().id(1L).content("Test").build();
        Comment comment2 = Comment.builder().id(1L).content("Test").build();

        // Then
        assertEquals(comment1.hashCode(), comment2.hashCode());
    }
} 