package com.studentos.service;

import com.studentos.dto.AuthResponse;
import com.studentos.dto.RegisterRequest;
import com.studentos.model.User;
import com.studentos.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository,
                        PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public User register(RegisterRequest request) {
        if(userRepository.existsByUserName(request.getUserName())) {
            throw new RuntimeException("Username already taken");
        }

        if(userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        // creating user
        User user = new User();
        user.setUserName(request.getUserName());
        user.setEmail(request.getEmail());

        // hashing the password
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));

        // setting role
        user.setRole("USER");

        // save and return
        return userRepository.save(user);
    }
}
