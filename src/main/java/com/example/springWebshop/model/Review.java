package com.example.springWebshop.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

// _____________________________________________________________________________

@Entity
@Table(name = "reviews")
public class Review {

    // 1
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 2
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false,
                foreignKey = @ForeignKey(name = "fk_review_product"))
    private Product product;

    // 3
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false,
                foreignKey = @ForeignKey(name = "fk_review_user"))
    private User user;

    // 4
    @Column(name = "rating", nullable = false)
    private int rating;

    // 5
    @Column(name = "comment", columnDefinition = "TEXT")
    private String comment;

    // 6
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // 7
    @Column(name = "is_approved", nullable = false)
    private boolean isApproved;

// _____________________________________________________________________________
// Explicit no-arg constructor

    public Review() {}

// _____________________________________________________________________________
// Lifecycle hooks for timestamps.

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

// _____________________________________________________________________________
// Getters & Setters

    // 1
    public Long getId() {
        return id;
    }

    // 2
    public Product getProduct() {
        return product;
    }

    // 2
    public void setProduct(Product product) {
        this.product = product;
    }

    // 3
    public User getUser() {
        return user;
    }

    // 3
    public void setUser(User user) {
        this.user = user;
    }

    // 4
    public int getRating() {
        return rating;
    }

    // 4
    public void setRating(int rating) {
        this.rating = rating;
    }

    // 5
    public String getComment() {
        return comment;
    }

    // 5
    public void setComment(String comment) {
        this.comment = comment;
    }

    // 6
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // 7
    public boolean isApproved() {
        return isApproved;
    }

    // 7
    public void setApproved(boolean isApproved) {
        this.isApproved = isApproved;
    }
}
