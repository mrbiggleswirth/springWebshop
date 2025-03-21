package com.example.springWebshop.dto;

public class LoginRequest {
    private String email;     // 1 -
    private String password;  // 2 -

    // Default constructor
    public LoginRequest() {}

// _____________________________________________________________________________
// Getters & Setters

    // 1
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // 2
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
