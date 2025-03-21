package com.example.springWebshop.dto;

public class RegisterResponse {
    private String token;    // 1 -
    private String message;  // 2 -

    // Default constructor
    public RegisterResponse() {}

    // Constructor with fields
    public RegisterResponse(String token, String message) {
        this.token = token;
        this.message = message;
    }

// _____________________________________________________________________________
// Getters & Setters

    // 1
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    // 2
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
