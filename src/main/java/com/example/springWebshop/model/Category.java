package com.example.springWebshop.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

// _____________________________________________________________________________

@Entity
@Table(name = "categories")
public class Category {

    // 1
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 2
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    // 3
    @Column(name = "description", length = 255)
    private String description;

// _____________________________________________________________________________

    /**
     * 4-A1: Parent category (self-referencing relationship).
     */
    @ManyToOne
    @JoinColumn(name = "parent_id", foreignKey = @ForeignKey(name = "fk_category_parent"))
    @JsonBackReference // Ignore this field during JSON serialization to prevent recursion.
    private Category parent;

    /**
     * 4-A2: Subcategories for a category (self-referencing relationship).
     */
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // Serialize this field.
    private List<Category> subcategories = new ArrayList<>();

    /**
     * 4-B: Products belonging to this category.
     */
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products = new ArrayList<>();

// _____________________________________________________________________________

    // 5
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // 6
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

// _____________________________________________________________________________
// Explicit no-arg constructor.

    public Category() {}

// _____________________________________________________________________________
// Lifecycle hooks for timestamps.

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

// _____________________________________________________________________________
// Getters & Setters

    // 1
    public Long getId() {
        return id;
    }

    // 2
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // 3
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

// _____________________________________________________________________________

    /**
     * 4-A1: Parent category (self-referencing relationship).
     */
    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }

    /**
     * 4-A2: Subcategories for a category (self-referencing relationship).
     */
    public List<Category> getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(List<Category> subcategories) {
        this.subcategories = subcategories;
    }

    /**
     * 4-B: Products belonging to this category.
     */
    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

// _____________________________________________________________________________

    // 5
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // 6
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
