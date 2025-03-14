package com.example.springWebshop.controller;

import java.util.List;

import com.example.springWebshop.mapper.ProductMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.springWebshop.dto.ProductDto;
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

// _____________________________________________________________________________
// Example of transitioning to DTO.

    /*
    // Get all products.
    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }
     */

    /*
    // Get all products.
    @GetMapping("/products")
    public List<ProductDto> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return products.stream()
            .map(ProductMapper.INSTANCE::productToProductDto)
            .toList();
    }
    */

    // Get all products.
    @GetMapping("/products")
    public List<ProductDto> getAllProducts() {
        return productService.getAllProducts();
    }

// _____________________________________________________________________________

    // Get a product by ID.
    @GetMapping("/product/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    // Get a products by name.
    @GetMapping("/products/name/{name}")
    public List<Product> getProductsByNameIgnoreCaseStartingWith(@PathVariable String name) {
        return productService.getProductsByNameIgnoreCaseStartingWith(name);
    }

    /**
     * TODO: Optional manufacturer, not yet implemented.
     */

    /*
    // Get a products by name.
    @GetMapping("/products/name")
    public List<Product> getProductsByNameAndFilters(
        @RequestParam String name,
        @RequestParam(required = false) String manufacturer
    ) {
        // return productService.getProductsByNameIgnoreCaseStartingWith(name);
        return productService.getProductsByNameAndFilters(
            name,
            manufacturer
        );
    }
    */

}
