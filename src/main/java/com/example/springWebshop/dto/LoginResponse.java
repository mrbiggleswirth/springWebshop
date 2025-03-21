package com.example.springWebshop.dto;

public class LoginResponse {
    private String token;     // 1 -
    private String email;     // 2 -
    private String username;  // 3 -

    // Default constructor
    public LoginResponse() {}

    // Constructor with fields
    public LoginResponse(String token, String email, String username) {
        this.token = token;
        this.email = email;
        this.username = username;
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
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // 3
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
