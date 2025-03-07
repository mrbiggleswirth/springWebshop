package com.example.springWebshop.model;

import java.math.BigDecimal;

import jakarta.persistence.*;

import com.example.springWebshop.model.Order;
import com.example.springWebshop.model.Product;

// _____________________________________________________________________________

@Entity
@Table(name = "order_items")
public class OrderItem {

    // 1
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // 2
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    // 3
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    // 4
    @Column(nullable = false)
    private Integer quantity;

    // 5
    @Column(name = "unit_price", nullable = false)
    private BigDecimal unitPrice;

    // 6
    @Column(nullable = false)
    private BigDecimal subtotal;

// _____________________________________________________________________________
// Explicit no-arg constructor.

    public OrderItem() {}

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
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    // 4
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    // 5
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    // 6
    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }
}
