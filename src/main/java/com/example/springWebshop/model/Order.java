package com.example.springWebshop.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;

// _____________________________________________________________________________

@Entity
@Table(name = "orders")
public class Order {

    // 1
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 2
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false,
                foreignKey = @ForeignKey(name = "fk_order_user"))
    private User user;

    // 3
    @Column(name = "order_date", nullable = false,
            columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime orderDate;

    // 4
    @Column(name = "status", length = 20, nullable = false,
            columnDefinition = "VARCHAR(20) DEFAULT 'pending'")
    private String status;

    // 5
    @Column(name = "total_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    // 6
    @Column(name = "tax", precision = 10, scale = 2,
            columnDefinition = "DECIMAL(10,2) DEFAULT 0.00")
    private BigDecimal tax;

    // 7
    @Column(name = "shipping_cost", precision = 10, scale = 2,
            columnDefinition = "DECIMAL(10,2) DEFAULT 0.00")
    private BigDecimal shippingCost;

    /**
     *
     * TODO: Don't forget that missing SPACE causes MySQL error!!
     *
     * columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP" +
     *                    "ON UPDATE CURRENT_TIMESTAMP")
     */

    // 8
    @Column(name = "updated_at",
            columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP " +
                               "ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;

    // _____________________________________________________________________________
    // Explicit no-arg constructor.

    public Order() {}

    // _____________________________________________________________________________
    // Lifecycle hooks for timestamps.

    @PrePersist
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
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // 3
    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    // 4
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // 5
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    // 6
    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    // 7
    public BigDecimal getShippingCost() {
        return shippingCost;
    }

    public void setShippingCost(BigDecimal shippingCost) {
        this.shippingCost = shippingCost;
    }

    // 8
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
