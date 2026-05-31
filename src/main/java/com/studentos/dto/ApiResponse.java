package com.studentos.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

// Every API response has the same shape - clients always know what to expect
// success: tells client immediately if request succeeded
// data: the actual payload (null for error responses)
// error: error message (null for success responses)
// timestamp: when the response was generated
public class ApiResponse<T> {

    private boolean success;
    private T data;
    private String error;
    private LocalDateTime timestamp;

    // Private constructor - force use of static factories
    private ApiResponse() {
        this.timestamp = LocalDateTime.now();
    }

    // Static factory for success responses
    public static <T> ApiResponse<T> success(T data) {
        ApiResponse<T> response = new ApiResponse<>();
        response.success = true;
        response.data = data;
        return response;
    }

    // Static factory for error responses
    public static <T> ApiResponse<T> error(String message) {
        ApiResponse<T> response = new ApiResponse<>();
        response.success = false;
        response.error = message;
        return response;
    }

    // Getters — needed for Jackson serialization

    public boolean isSuccess() {
        return success;
    }

    public T getData() {
        return data;
    }

    public String getError() {
        return error;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
