package com.studentos.dto;

import jakarta.validation.constraints.*;



import java.time.LocalDate;

// This class represents what the client sends when creating a user
// It has NO id field - the server assigns the id
// It has NO createdAt - the server sets this
// The client only provides what they actually control
public class RegisterRequest {

    @NotBlank(message = "User name is required")
    @Size(min = 3, max = 50, message = "User name should be at-least 3 characters and cannot exceed 50 characters")
    private String userName;

    @NotBlank(message = "Email is required")
    @Email
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password should be at-least 8 characters")
    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
