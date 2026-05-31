package com.studentos.exception;

import com.studentos.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

// @RestControllerAdvice: this class handles exceptions from ALL controllers
// WHY one place: without this, each controller catches its own exceptions
// differently — inconsistent response shapes, duplicated error handling code
// With this: one class, one consistent response format for all errors
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handles @Valid failures in @RequestBody
    // When @NotBlank, @NotNull, @Pattern fail → Spring throws this
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationErrors(
            MethodArgumentNotValidException ex) {

        // Collect all field errors into one readable string
        // Example: "title: Title is required, type: Type must be Assignment, Exam, or Project"
        String errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)  // 400
                .body(ApiResponse.error(errors));
    }

    // Handles invalid JSON (malformed body, wrong types)
    // This was causing your original POST error with 'int' and null
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Void>> handleMessageNotReadable(
            HttpMessageNotReadableException ex) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)  // 400
                .body(ApiResponse.error("Invalid request body: " + ex.getMostSpecificCause().getMessage()));
    }

    // Handles task not found — 404
    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleTaskNotFound(
            TaskNotFoundException ex) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)  // 404
                .body(ApiResponse.error(ex.getMessage()));
    }

    // Catches everything else — prevents stack traces reaching the client
    // Stack traces in API responses are a security risk:
    // they reveal your internal structure, library versions, file paths
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGenericError(Exception ex) {

        // Log it internally (you would use a logger in production)
        System.err.println("Unexpected error: " + ex.getMessage());
        ex.printStackTrace();

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)  // 500
                .body(ApiResponse.error("An unexpected error occurred. Please try again."));
        // Never send ex.getMessage() for generic exceptions to the client
        // It might contain sensitive internal information
    }
}