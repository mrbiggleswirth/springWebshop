package com.example.springWebshop.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

// _____________________________________________________________________________

@Entity
@Table(name = "inventory")
public class Inventory {

    // 1
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 2
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false,
                foreignKey = @ForeignKey(name = "fk_inventory_product"))
    private Product product;

    // 3
    @Column(name = "quantity", nullable = false,
            columnDefinition = "INT DEFAULT 0")
    private int quantity;

    // 4
    @Column(name = "last_updated",
            columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP " +
                               "ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime lastUpdated;

    // 5
    @Column(name = "warehouse_location", length = 100)
    private String warehouseLocation;

// _____________________________________________________________________________
// Explicit no-arg constructor

    public Inventory() {}

// _____________________________________________________________________________
// Lifecycle hooks for timestamps.

    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        lastUpdated = LocalDateTime.now();
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
    public int getQuantity() {
        return quantity;
    }

    // 3
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // 4
    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    // 4
    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    // 5
    public String getWarehouseLocation() {
        return warehouseLocation;
    }

    // 5
    public void setWarehouseLocation(String warehouseLocation) {
        this.warehouseLocation = warehouseLocation;
    }
}
