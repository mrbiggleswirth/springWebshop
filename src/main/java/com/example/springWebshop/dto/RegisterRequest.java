package com.example.springWebshop.dto;

public class RegisterRequest {
    private String username;    // 1 -
    private String email;       // 2 -
    private String password;    // 3 -
    private String firstName;   // 4 -
    private String lastName;    // 5 -

    // Default constructor
    public RegisterRequest() {}

// _____________________________________________________________________________
// Getters & Setters

    // 1
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // 2
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // 3
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // 4
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    // 5
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
