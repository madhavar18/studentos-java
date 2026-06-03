package com.studentos.dto;


import org.springframework.web.bind.annotation.ResponseBody;

@ResponseBody
public class AuthResponse {
    private String message;
    private String userName;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
