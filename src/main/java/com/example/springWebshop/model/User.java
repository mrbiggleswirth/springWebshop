package com.example.springWebshop.model;

import java.time.LocalDateTime;
import jakarta.persistence.*;

// _____________________________________________________________________________

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 1
    @Column(unique = true, nullable = false, length = 50)
    private String username;

    // 2
    @Column(unique = true, nullable = false, length = 100)
    private String email;

    // 3
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    // 4
    @Column(name = "first_name", length = 100)
    private String firstName;

    // 5
    @Column(name = "last_name", length = 100)
    private String lastName;

    // 6
    @Column(length = 255)
    private String address;

    // 7
    @Column(length = 20)
    private String phone;

    // 8
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // 9
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // 10
    @Column(name = "is_active")
    private boolean isActive;

// _____________________________________________________________________________
// Default constructor

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
    public String getUsername() {
        return username;
    }

    // 1
    public void setUsername(String username) {
        this.username = username;
    }

    // 2
    public String getEmail() {
        return email;
    }

    // 2
    public void setEmail(String email) {
        this.email = email;
    }

    // 3
    public String getPasswordHash() {
        return passwordHash;
    }

    // 3
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    // 4
    public String getFirstName() {
        return firstName;
    }

    // 4
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    // 5
    public String getLastName() {
        return lastName;
    }

    // 5
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    // 6
    public String getAddress() {
        return address;
    }

    // 6
    public void setAddress(String address) {
        this.address = address;
    }

    // 7
    public String getPhone() {
        return phone;
    }

    // 7
    public void setPhone(String phone) {
        this.phone = phone;
    }

    // 8
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // 8
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // 9
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    // 9
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    // 10
    public boolean isActive() {
        return isActive;
    }

    // 10
    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }
}
