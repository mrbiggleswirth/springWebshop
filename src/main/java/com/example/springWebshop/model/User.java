package com.example.springWebshop.model;

import java.time.LocalDateTime;
import jakarta.persistence.*;

// _____________________________________________________________________________

@Entity
@Table(name = "users")
public class User {

    // 1
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 2
    @Column(unique = true, nullable = false, length = 50)
    private String username;

    // 3
    @Column(unique = true, nullable = false, length = 100)
    private String email;

    // 4
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    // 5
    @Column(name = "first_name", length = 100)
    private String firstName;

    // 6
    @Column(name = "last_name", length = 100)
    private String lastName;

    // 7
    @Column(length = 255)
    private String address;

    // 8
    @Column(length = 20)
    private String phone;

    // 9
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // 10
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // 11
    @Column(name = "is_active")
    private boolean isActive;

// _____________________________________________________________________________
// Explicit no-arg constructor.

    public User() {
        /**
         * This constructor is needed by JPA to instantiate entity class.
         * It is empty because JPA handles field population via reflection
         * and setters.
         */
    }

// _____________________________________________________________________________
// Getters & Setters

    // 1
    public Long getId() {
        return id;
    }

    // 2
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // 3
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // 4
    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    // 5
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    // 6
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    // 7
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    // 8
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    // 9
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // 10
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    // 11
    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }
}
