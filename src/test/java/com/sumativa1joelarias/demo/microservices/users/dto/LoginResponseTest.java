package com.sumativa1joelarias.demo.microservices.users.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LoginResponseTest {

    @Test
    void whenCreatedWithToken_shouldSetTokenOnly() {
        // Given
        String token = "test-token";

        // When
        LoginResponse response = new LoginResponse(token);

        // Then
        assertEquals(token, response.getToken());
        assertNull(response.getUserId());
        assertNull(response.getRole());
    }

    @Test
    void whenUsingNoArgsConstructor_shouldCreateEmptyResponse() {
        // When
        LoginResponse response = new LoginResponse();

        // Then
        assertNull(response.getToken());
        assertNull(response.getUserId());
        assertNull(response.getRole());
    }

    @Test
    void testSettersAndGetters() {
        // Given
        LoginResponse response = new LoginResponse();
        String token = "test-token";
        Long userId = 1L;
        String role = "USER";

        // When
        response.setToken(token);
        response.setUserId(userId);
        response.setRole(role);

        // Then
        assertEquals(token, response.getToken());
        assertEquals(userId, response.getUserId());
        assertEquals(role, response.getRole());
    }
} 