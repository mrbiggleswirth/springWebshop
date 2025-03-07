package com.example.springWebshop.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

// _____________________________________________________________________________

@Entity
@Table(name = "shipping")
public class Shipping {

    // 1
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // 2
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    // 3
    @Column(name = "recipient_name", nullable = false, length = 100)
    private String recipientName;

    // 4
    @Column(name = "address", nullable = false, length = 255)
    private String address;

    // 5
    @Column(name = "phone", length = 20)
    private String phone;

    // 6
    @Column(name = "shipping_method", nullable = false, length = 50)
    private String shippingMethod;

    // 7
    @Column(name = "tracking_number", length = 50)
    private String trackingNumber;

    // 8
    @Column(name = "status", length = 20, columnDefinition = "VARCHAR(20) DEFAULT 'processing'")
    private String status;

    // 9
    @Column(name = "estimated_delivery")
    private LocalDateTime estimatedDelivery;

// _____________________________________________________________________________
// Explicit no-arg constructor.

    public Shipping() {}

// _____________________________________________________________________________
// Lifecycle hooks for timestamps.

    @PrePersist
    protected void onCreate() {
        status = "processing"; // Default value.

        /**
         * Ternary conditional operator (a compact 'if-else' statement).
         * The general form of the ternary operator is:
         *
         *     condition ? valueIfTrue : valueIfFalse;
         *
         *     if (estimatedDelivery != null) {
         *         estimatedDelivery = estimatedDelivery;
         *     } else {
         *         estimatedDelivery = LocalDateTime.now().plusDays(7);
         *     }
         *
         */

        // Default estimate is 7 days if NULL.
        estimatedDelivery = estimatedDelivery != null
            ? estimatedDelivery
            : LocalDateTime.now().plusDays(7);
    }

    @PreUpdate
    protected void onUpdate() {
        // No specific behavior on update, but could be added if necessary.
    }

// _____________________________________________________________________________
// Getters & Setters

    // 1
    public Integer getId() {
        return id;
    }

    // 2
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    // 3
    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    // 4
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    // 5
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    // 6
    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    // 7
    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    // 8
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // 9
    public LocalDateTime getEstimatedDelivery() {
        return estimatedDelivery;
    }

    public void setEstimatedDelivery(LocalDateTime estimatedDelivery) {
        this.estimatedDelivery = estimatedDelivery;
    }
}
