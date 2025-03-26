package com.example.springWebshop.controller;

import jakarta.validation.Valid;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.springWebshop.dto.ProductDto;
import com.example.springWebshop.model.Product;
import com.example.springWebshop.service.ProductService;

// _____________________________________________________________________________

@RestController
@RequestMapping("/api/admin/products")
public class AdminProductController {
    private final ProductService productService;

    public AdminProductController(ProductService productService) {
        this.productService = productService;
    }

// _____________________________________________________________________________

    // Get all products (admin view)
    @GetMapping
    public List<ProductDto> getAllProducts() {
        return productService.getAllProducts();
    }

// _____________________________________________________________________________

    // Create new product
    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto) {
        ProductDto createdProduct = productService.createProduct(productDto);
        return ResponseEntity.ok(createdProduct);
    }

// _____________________________________________________________________________

    // Update existing product
    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(
        @PathVariable Long id,
        @Valid @RequestBody ProductDto productDto) {
        ProductDto updatedProduct = productService.updateProduct(id, productDto);
        if (updatedProduct != null) {
            return ResponseEntity.ok(updatedProduct);
        }
        return ResponseEntity.notFound().build();
    }

// _____________________________________________________________________________

    // Delete product
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        boolean deleted = productService.deleteProduct(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

// _____________________________________________________________________________

    // Update stock quantity
    @PatchMapping("/{id}/stock")
    public ResponseEntity<ProductDto> updateStockQuantity(
        @PathVariable Long id,
        @RequestParam Integer quantity) {
        ProductDto updatedProduct = productService.updateStockQuantity(id, quantity);
        if (updatedProduct != null) {
            return ResponseEntity.ok(updatedProduct);
        }
        return ResponseEntity.notFound().build();
    }

// _____________________________________________________________________________

    // Toggle product availability
    @PatchMapping("/{id}/availability")
    public ResponseEntity<ProductDto> toggleAvailability(@PathVariable Long id) {
        ProductDto updatedProduct = productService.toggleProductAvailability(id);
        if (updatedProduct != null) {
            return ResponseEntity.ok(updatedProduct);
        }
        return ResponseEntity.notFound().build();
    }
}
