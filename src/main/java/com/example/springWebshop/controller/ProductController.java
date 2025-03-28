package com.example.springWebshop.controller;

import java.util.List;

import com.example.springWebshop.mapper.ProductMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.springWebshop.dto.ProductDto;
// import com.example.springWebshop.model.Product;
import com.example.springWebshop.service.ProductService;

// _____________________________________________________________________________

@RestController
@RequestMapping("/api/products")
public class ProductController {
    /**
     * Constructor injection is preferred over @Autowired on fields.
     */
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

// _____________________________________________________________________________

    // Get all products.
    @GetMapping
    public List<ProductDto> getAllProducts() {
        return productService.getAllProducts();
    }

// _____________________________________________________________________________

    // Get product by ID, enhanced safer DTO version.
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        ProductDto product = productService.getProductDtoById(id);
        if (product != null) {
            return ResponseEntity.ok(product);
        }
        return ResponseEntity.notFound().build();
    }

// _____________________________________________________________________________

    // Search products by name.
    @GetMapping("/search")
    public List<ProductDto> searchProducts(@RequestParam String query) {
        return productService.searchProductsByName(query);
    }

    /**
     * TODO: Optional manufacturer, not yet implemented.
     */

// _____________________________________________________________________________

    // Filter products by category.
    @GetMapping("/category/{categoryId}")
    public List<ProductDto> getProductsByCategory(@PathVariable Long categoryId) {
        return productService.getProductsByCategory(categoryId);
    }

// _____________________________________________________________________________

    // Filter products by price range.
    @GetMapping("/price")
    public List<ProductDto> getProductsByPriceRange(
        @RequestParam(required = false) Double min,
        @RequestParam(required = false) Double max) {
        return productService.getProductsByPriceRange(min, max);
    }
}
