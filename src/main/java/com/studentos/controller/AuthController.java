package com.studentos.controller;

import com.studentos.dto.ApiResponse;
import com.studentos.dto.AuthResponse;
import com.studentos.dto.RegisterRequest;
import com.studentos.model.User;
import com.studentos.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register (
            @Valid @RequestBody RegisterRequest request) {

            User saved = authService.register(request);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(ApiResponse.success(new AuthResponse()));
    }
}
