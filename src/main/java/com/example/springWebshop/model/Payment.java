package com.example.springWebshop.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;

// _____________________________________________________________________________

@Entity
@Table(name = "payments", indexes = {
    @Index(name = "idx_order", columnList = "order_id"),
    @Index(name = "idx_transaction", columnList = "transaction_id")
})
public class Payment {

    // 1
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // 2
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false, foreignKey = @ForeignKey(name = "fk_payment_order"))
    private Order order;

    // 3
    @Column(name = "payment_method", nullable = false, length = 20)
    private String paymentMethod;

    // 4
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    // 5
    @Column(name = "transaction_id", length = 50)
    private String transactionId;

    // 6
    @Column(name = "payment_date", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime paymentDate;

    // 7
    @Column(nullable = false, length = 20)
    private String status = "pending";

// _____________________________________________________________________________
// Explicit no-arg constructor.

    public Payment() {}

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
    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    // 4
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    // 5
    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    // 6
    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    // 7
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
