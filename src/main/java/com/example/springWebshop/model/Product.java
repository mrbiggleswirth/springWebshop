package com.example.springWebshop.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

// _____________________________________________________________________________

/**
 *
 * For product prices, avoid double and float because they can introduce
 * rounding errors due to floating-point precision issues. Instead, use
 * 'BigDecimal', which provides precise arithmetic for financial calculations.
 *
 * Use 'double' for scientific calculations or cases where
 * small precision errors don’t matter.
 *
 * Use 'float' only for memory-constrained scenarios, like graphics rendering.
 *
 */

@Entity
@Table(name = "products")
@JsonIgnoreProperties({"category"})
public class Product {

    // 1
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 2
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    // 3
    @Lob // Maps to TEXT in most databases.
    private String description;

    // 4
    @Column(name = "price", precision = 10, scale = 2, nullable = false)
    private BigDecimal price;

    /**
     * 5: Product belongs to a single category.
     */
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = true,
                foreignKey = @ForeignKey(name = "fk_product_category"))
    /**
     * @JsonManagedReference // This ensures proper serialization in the Product class.
     * Might be needed again later when working with DTOs!
     */
    private Category category;

    // 6
    @Column(name = "image_url", length = 255)
    private String imageUrl;

    // 7
    @Column(name = "stock_quantity", nullable = false,
            columnDefinition = "INT DEFAULT 0")
    private Integer stockQuantity = 0;

    // 8
    @Column(name = "is_available", nullable = false,
            columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean isAvailable = true;

    // 9
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // 10
    @Column(name = "updated_at")
    private  LocalDateTime updatedAt;

// _____________________________________________________________________________
// Explicit no-arg constructor.

    public Product() {}

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

    // 4
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    // 5
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    // 6
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    // 7
    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    // 8
    public Boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }

    // 9
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // 10
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
