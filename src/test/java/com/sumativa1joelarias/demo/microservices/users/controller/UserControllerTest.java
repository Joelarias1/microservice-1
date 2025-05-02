package com.sumativa1joelarias.demo.microservices.users.controller;

import com.sumativa1joelarias.demo.microservices.users.dto.UserDTO;
import com.sumativa1joelarias.demo.microservices.users.dto.UserManagementRequest;
import com.sumativa1joelarias.demo.microservices.users.enums.UserRole;
import com.sumativa1joelarias.demo.microservices.users.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private UserDTO userDTO1;
    private UserDTO userDTO2;
    private UserManagementRequest userRequest;

    @BeforeEach
    void setUp() {
        userDTO1 = new UserDTO();
        userDTO1.setId(1L);
        userDTO1.setUsername("user1");
        userDTO1.setEmail("user1@test.com");
        userDTO1.setRole(UserRole.USER);
        userDTO1.setStatus("ACTIVE");

        userDTO2 = new UserDTO();
        userDTO2.setId(2L);
        userDTO2.setUsername("admin1");
        userDTO2.setEmail("admin1@test.com");
        userDTO2.setRole(UserRole.ADMIN);
        userDTO2.setStatus("ACTIVE");

        userRequest = new UserManagementRequest();
        userRequest.setUsername("newUser");
        userRequest.setEmail("new@test.com");
        userRequest.setPassword("password123");
    }

    @Test
    void getAllUsers_shouldReturnListOfUsers() {
        List<UserDTO> users = Arrays.asList(userDTO1, userDTO2);
        when(userService.getAllUsers()).thenReturn(users);

        ResponseEntity<List<UserDTO>> response = userController.getAllUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void getUserById_whenUserExists_shouldReturnUser() {
        when(userService.getUserById(1L)).thenReturn(userDTO1);

        ResponseEntity<UserDTO> response = userController.getUserById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(userDTO1.getId(), response.getBody().getId());
        verify(userService, times(1)).getUserById(1L);
    }

    @Test
    void getUserById_whenUserDoesNotExist_shouldThrowException() {
        when(userService.getUserById(99L)).thenThrow(new RuntimeException("User not found"));

        assertThrows(RuntimeException.class, () -> {
            userController.getUserById(99L);
        });

        verify(userService, times(1)).getUserById(99L);
    }

    @Test
    void createUser_shouldReturnCreatedUser() {
        UserDTO createdUser = new UserDTO();
        createdUser.setId(3L);
        createdUser.setUsername(userRequest.getUsername());
        createdUser.setEmail(userRequest.getEmail());
        createdUser.setRole(UserRole.USER);
        createdUser.setStatus("ACTIVE");

        when(userService.createUser(any(UserManagementRequest.class))).thenReturn(createdUser);

        ResponseEntity<UserDTO> response = userController.createUser(userRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("newUser", response.getBody().getUsername());
        verify(userService, times(1)).createUser(any(UserManagementRequest.class));
    }

    @Test
    void updateUser_shouldReturnUpdatedUser() {
        UserDTO updatedUser = new UserDTO();
        updatedUser.setId(1L);
        updatedUser.setUsername("updatedUser");
        updatedUser.setEmail("update@test.com");
        updatedUser.setRole(UserRole.ADMIN);
        updatedUser.setStatus("INACTIVE");

        UserManagementRequest updateRequest = new UserManagementRequest();
        updateRequest.setUsername("updatedUser");
        updateRequest.setEmail("update@test.com");

        when(userService.updateUser(eq(1L), any(UserManagementRequest.class))).thenReturn(updatedUser);

        ResponseEntity<UserDTO> response = userController.updateUser(1L, updateRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("updatedUser", response.getBody().getUsername());
        verify(userService, times(1)).updateUser(eq(1L), any(UserManagementRequest.class));
    }

    @Test
    void deleteUser_shouldReturnNoContent() {
        doNothing().when(userService).deleteUser(1L);

        ResponseEntity<Void> response = userController.deleteUser(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(userService, times(1)).deleteUser(1L);
    }

     @Test
    void deleteUser_whenUserDoesNotExist_shouldThrowException() {
        doThrow(new RuntimeException("User not found to delete")).when(userService).deleteUser(99L);

        assertThrows(RuntimeException.class, () -> {
            userController.deleteUser(99L);
        });

        verify(userService, times(1)).deleteUser(99L);
    }

} 