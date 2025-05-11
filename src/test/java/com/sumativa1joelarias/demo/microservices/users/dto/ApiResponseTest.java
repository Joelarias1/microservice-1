package com.sumativa1joelarias.demo.microservices.users.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ApiResponseTest {

    @Test
    void success_shouldCreateSuccessResponse() {
        // Given
        String testData = "Test Data";
        String message = "Success Message";

        // When
        ApiResponse<String> response = ApiResponse.success(testData, message);

        // Then
        assertTrue(response.isSuccess());
        assertEquals(message, response.getMessage());
        assertEquals(testData, response.getData());
        assertNull(response.getError());
    }

    @Test
    void error_shouldCreateErrorResponse() {
        // Given
        String errorMessage = "Error Message";

        // When
        ApiResponse<Object> response = ApiResponse.error(errorMessage);

        // Then
        assertFalse(response.isSuccess());
        assertEquals(errorMessage, response.getError());
        assertNull(response.getData());
        assertNull(response.getMessage());
    }

    @Test
    void builderAndGetters_shouldWorkCorrectly() {
        // Given
        String testData = "Test Data";
        String message = "Test Message";
        String error = "Test Error";

        // When
        ApiResponse<String> response = ApiResponse.<String>builder()
                .success(true)
                .message(message)
                .data(testData)
                .error(error)
                .build();

        // Then
        assertTrue(response.isSuccess());
        assertEquals(message, response.getMessage());
        assertEquals(testData, response.getData());
        assertEquals(error, response.getError());
    }

    @Test
    void noArgsConstructor_shouldCreateEmptyResponse() {
        // When
        ApiResponse<Object> response = new ApiResponse<>();

        // Then
        assertFalse(response.isSuccess());
        assertNull(response.getMessage());
        assertNull(response.getData());
        assertNull(response.getError());
    }

    @Test
    void allArgsConstructor_shouldCreateFullResponse() {
        // Given
        boolean success = true;
        String message = "Test Message";
        String data = "Test Data";
        String error = "Test Error";

        // When
        ApiResponse<String> response = new ApiResponse<>(success, message, data, error);

        // Then
        assertEquals(success, response.isSuccess());
        assertEquals(message, response.getMessage());
        assertEquals(data, response.getData());
        assertEquals(error, response.getError());
    }

    @Test
    void setters_shouldUpdateValues() {
        // Given
        ApiResponse<String> response = new ApiResponse<>();

        // When
        response.setSuccess(true);
        response.setMessage("Updated Message");
        response.setData("Updated Data");
        response.setError("Updated Error");

        // Then
        assertTrue(response.isSuccess());
        assertEquals("Updated Message", response.getMessage());
        assertEquals("Updated Data", response.getData());
        assertEquals("Updated Error", response.getError());
    }
} 