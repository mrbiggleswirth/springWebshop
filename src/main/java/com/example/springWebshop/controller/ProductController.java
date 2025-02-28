package com.example.springWebshop.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.springWebshop.model.Product;
import com.example.springWebshop.service.ProductService;

// _____________________________________________________________________________

@RestController
public class ProductController {
    /**
     * Constructor injection is preferred over @Autowired on fields.
     */
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Get all products.
    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    // Get a product by ID.
    @GetMapping("/product/{id}")
    public Product getUserById(@PathVariable Long id) {
        return productService.getProductById(id);
    }
}
