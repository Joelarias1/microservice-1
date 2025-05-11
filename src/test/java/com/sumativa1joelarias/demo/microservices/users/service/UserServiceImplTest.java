package com.sumativa1joelarias.demo.microservices.users.service;

import com.sumativa1joelarias.demo.microservices.users.dto.UserDTO;
import com.sumativa1joelarias.demo.microservices.users.dto.UserManagementRequest;
import com.sumativa1joelarias.demo.microservices.users.enums.UserRole;
import com.sumativa1joelarias.demo.microservices.users.exception.ResourceNotFoundException;
import com.sumativa1joelarias.demo.microservices.users.model.User;
import com.sumativa1joelarias.demo.microservices.users.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserTestDataService testDataService;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private UserServiceImpl userService;

    private User testUser;
    private UserManagementRequest testRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testUser");
        testUser.setEmail("test@example.com");
        testUser.setPassword("hashedPassword");
        testUser.setRole(UserRole.USER);
        testUser.setStatus("ACTIVE");
        testUser.setFullName("Test User");
        testUser.setAvatar("avatar.jpg");
        testUser.setCreatedAt(LocalDateTime.now());

        testRequest = new UserManagementRequest();
        testRequest.setUsername("testUser");
        testRequest.setEmail("test@example.com");
        testRequest.setPassword("password123");
        testRequest.setRole(UserRole.USER);
        testRequest.setStatus("ACTIVE");
        testRequest.setFullName("Test User");
        testRequest.setAvatar("avatar.jpg");

        when(passwordEncoder.encode(anyString())).thenReturn("hashedPassword");
        when(testDataService.generateTestUsers()).thenReturn(Arrays.asList(testUser));
        when(testDataService.getUserById(anyLong())).thenReturn(Optional.of(testUser));
        when(jdbcTemplate.update(anyString(), any(Object[].class))).thenReturn(1);

        // Inyectar manualmente los servicios
        ReflectionTestUtils.setField(userService, "testDataService", testDataService);
        ReflectionTestUtils.setField(userService, "jdbcTemplate", jdbcTemplate);
    }

    @Test
    void createUser_Success() {
        // Given
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        // When
        UserDTO result = userService.createUser(testRequest);

        // Then
        assertNotNull(result);
        assertEquals(testUser.getUsername(), result.getUsername());
        assertEquals(testUser.getEmail(), result.getEmail());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void createUser_DuplicateUsername() {
        // Given
        when(userRepository.existsByUsername(anyString())).thenReturn(true);

        // Then
        assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser(testRequest);
        });
    }

    @Test
    void updateUser_Success() {
        // Given
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        // When
        UserDTO result = userService.updateUser(1L, testRequest);

        // Then
        assertNotNull(result);
        assertEquals(testUser.getUsername(), result.getUsername());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void updateUser_NotFound() {
        // Given
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        when(testDataService.getUserById(1L)).thenReturn(Optional.empty());

        // Then
        assertThrows(ResourceNotFoundException.class, () -> {
            userService.updateUser(1L, testRequest);
        });
    }

    @Test
    void deleteUser_Success() {
        // Given
        when(userRepository.existsById(1L)).thenReturn(true);
        doNothing().when(userRepository).deleteById(1L);

        // When
        userService.deleteUser(1L);

        // Then
        verify(userRepository).deleteById(1L);
    }

    @Test
    void deleteUser_WithConstraintViolation() {
        // Given
        when(userRepository.existsById(1L)).thenReturn(true);
        doThrow(DataIntegrityViolationException.class)
            .doNothing()
            .when(userRepository).deleteById(1L);

        // When
        userService.deleteUser(1L);

        // Then
        verify(jdbcTemplate).update(contains("DELETE FROM comments"), eq(1L));
        verify(jdbcTemplate).update(contains("DELETE FROM posts"), eq(1L));
        verify(userRepository, times(2)).deleteById(1L);
    }

    @Test
    void getAllUsers_Success() {
        // Given
        when(userRepository.findAll()).thenReturn(Arrays.asList(testUser));

        // When
        var result = userService.getAllUsers();

        // Then
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(testUser.getUsername(), result.get(0).getUsername());
    }

    @Test
    void getAllUsers_EmptyList_UsesTestData() {
        // Given
        when(userRepository.findAll()).thenReturn(Arrays.asList());
        when(testDataService.generateTestUsers()).thenReturn(Arrays.asList(testUser));

        // When
        var result = userService.getAllUsers();

        // Then
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(testUser.getUsername(), result.get(0).getUsername());
    }

    @Test
    void updateProfile_Success() {
        // Given
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        // When
        UserDTO result = userService.updateProfile(1L, testRequest);

        // Then
        assertNotNull(result);
        assertEquals(testUser.getEmail(), result.getEmail());
        assertEquals(testUser.getFullName(), result.getFullName());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void updateProfile_EmailExists() {
        // Given
        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setEmail("old@example.com");

        User otherUser = new User();
        otherUser.setId(2L);
        otherUser.setEmail("test@example.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(otherUser));

        UserManagementRequest request = new UserManagementRequest();
        request.setEmail("test@example.com");

        // Then
        assertThrows(IllegalArgumentException.class, () -> {
            userService.updateProfile(1L, request);
        });
    }
} 