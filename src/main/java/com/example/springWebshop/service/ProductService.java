package com.example.springWebshop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.springWebshop.model.Product;
import com.example.springWebshop.repository.ProductRepository;

// _____________________________________________________________________________

@Service
public class ProductService {
    /**
     * Constructor injection is preferred over @Autowired on fields.
     */
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

// _____________________________________________________________________________

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        /**
         * Fetches product by ID or returns null.
         */
        return productRepository.findById(id).orElse(null);
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }
}
