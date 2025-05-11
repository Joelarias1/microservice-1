package com.sumativa1joelarias.demo.microservices.users.dto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginRequestTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void whenAllFieldsValid_shouldHaveNoViolations() {
        // Given
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setIdentifier("usuario@test.com");
        loginRequest.setPassword("password123");

        // When
        var violations = validator.validate(loginRequest);

        // Then
        assertTrue(violations.isEmpty());
    }

    @Test
    void whenIdentifierIsNull_shouldHaveViolation() {
        // Given
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPassword("password123");

        // When
        var violations = validator.validate(loginRequest);

        // Then
        assertEquals(1, violations.size());
        assertEquals("El identificador no puede estar vacío", 
            violations.iterator().next().getMessage());
    }

    @Test
    void whenIdentifierIsEmpty_shouldHaveViolation() {
        // Given
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setIdentifier("");
        loginRequest.setPassword("password123");

        // When
        var violations = validator.validate(loginRequest);

        // Then
        assertEquals(1, violations.size());
        assertEquals("El identificador no puede estar vacío", 
            violations.iterator().next().getMessage());
    }

    @Test
    void whenPasswordIsNull_shouldHaveViolation() {
        // Given
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setIdentifier("usuario@test.com");

        // When
        var violations = validator.validate(loginRequest);

        // Then
        assertEquals(1, violations.size());
        assertEquals("La contraseña no puede estar vacía", 
            violations.iterator().next().getMessage());
    }

    @Test
    void whenPasswordIsEmpty_shouldHaveViolation() {
        // Given
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setIdentifier("usuario@test.com");
        loginRequest.setPassword("");

        // When
        var violations = validator.validate(loginRequest);

        // Then
        assertEquals(1, violations.size());
        assertEquals("La contraseña no puede estar vacía", 
            violations.iterator().next().getMessage());
    }

    @Test
    void whenBothFieldsAreInvalid_shouldHaveTwoViolations() {
        // Given
        LoginRequest loginRequest = new LoginRequest();

        // When
        var violations = validator.validate(loginRequest);

        // Then
        assertEquals(2, violations.size());
    }

    @Test
    void testGettersAndSetters() {
        // Given
        LoginRequest loginRequest = new LoginRequest();
        String identifier = "test@example.com";
        String password = "password123";

        // When
        loginRequest.setIdentifier(identifier);
        loginRequest.setPassword(password);

        // Then
        assertEquals(identifier, loginRequest.getIdentifier());
        assertEquals(password, loginRequest.getPassword());
    }

    @Test
    void testEqualsAndHashCode() {
        // Given
        LoginRequest request1 = new LoginRequest();
        request1.setIdentifier("test@example.com");
        request1.setPassword("password123");

        LoginRequest request2 = new LoginRequest();
        request2.setIdentifier("test@example.com");
        request2.setPassword("password123");

        LoginRequest request3 = new LoginRequest();
        request3.setIdentifier("different@example.com");
        request3.setPassword("password123");

        // Then
        assertEquals(request1, request1);
        assertEquals(request1, request2);
        assertNotEquals(request1, request3);
        assertEquals(request1.hashCode(), request2.hashCode());
    }

    @Test
    void testToString() {
        // Given
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setIdentifier("test@example.com");
        loginRequest.setPassword("password123");

        // When
        String toString = loginRequest.toString();

        // Then
        assertTrue(toString.contains("test@example.com"));
        assertTrue(toString.contains("password123"));
    }
} 