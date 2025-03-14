package com.example.springWebshop.dto;

import java.math.BigDecimal;

import com.example.springWebshop.model.Product;

// _____________________________________________________________________________

public class ProductDto {
    private Long productId;                // 1 -
    private String productName;            // 2 -
    private String productDescription;     // 3 -
    private BigDecimal productPrice;       // 4 -
    private String productCategory;        // 5 -
    private String productImageUrl;        // 6 -
    private Integer productStockQuantity;  // 7 -
    private boolean productIsAvailable;    // 8 -

// _____________________________________________________________________________

    /**
     *  --- Constructor overloading ---
     *  Allows a class to have multiple constructors with the same name
     *  but different parameters.
     */

    // --- No-arg constructor ---
    public ProductDto() {}

    // --- Constructor that converts a Product to ProductDto ---
    public ProductDto(Product product) {
        this.productId = product.getId();
        this.productName = product.getName();
        this.productDescription = product.getDescription();
        this.productPrice = product.getPrice();

        this.productCategory = ( product.getCategory() != null )
            ? product.getCategory().getName()
            : null;

        this.productImageUrl = product.getImageUrl();
        this.productStockQuantity = product.getStockQuantity();
        this.productIsAvailable = product.getIsAvailable();
    }

    // --- Constructor for direct instantiation of ProductDto ---
    public ProductDto(
        Long productId,
        String productName,
        String productDescription,
        BigDecimal productPrice,
        String productCategory,
        String productImageUrl,
        Integer productStockQuantity,
        Boolean productIsAvailable
    ) {
        this.productId = productId;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
        this.productCategory = productCategory;
        this.productImageUrl = productImageUrl;
        this.productStockQuantity = productStockQuantity;
        this.productIsAvailable = productIsAvailable;
    }

// _____________________________________________________________________________
// Getters & Setters

    // 1
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    // 2
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    // 3
    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    // 4
    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    // 5
    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    // 6
    public String getProductImageUrl() {
        return productImageUrl;
    }

    public void setProductImageUrl(String productImageUrl) {
        this.productImageUrl = productImageUrl;
    }

    // 7
    public Integer getProductStockQuantity() {
        return productStockQuantity;
    }

    public void setProductStockQuantity(Integer productStockQuantity) {
        this.productStockQuantity = productStockQuantity;
    }

    // 8
    public Boolean getProductIsAvailable() {
        return productIsAvailable;
    }

    public void setProductIsAvailable(Boolean productIsAvailable) {
        this.productIsAvailable = productIsAvailable;
    }
}
