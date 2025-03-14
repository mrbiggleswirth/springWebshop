package com.example.springWebshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.springWebshop.dto.ProductDto;
import com.example.springWebshop.mapper.ProductMapper;
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
// Example of transitioning to DTO.

    /*
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
     */

    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll(); // Fetches Product entities.
        return products.stream()
            .map(ProductMapper.INSTANCE::productToProductDto) // Converts Product to ProductDto.
            .toList();
    }

// _____________________________________________________________________________

    public Product getProductById(Long id) {
        /**
         * Fetches product by ID or returns null.
         */
        return productRepository.findById(id).orElse(null);
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

// _____________________________________________________________________________
// Searches

    public List<Product> getProductsByNameIgnoreCaseStartingWith(String name) {
        System.out.println("Searching for products with name: " + name);
        return productRepository.findByNameIgnoreCaseStartingWith(name);
    }

    // For testing only?
    /*
    public Optional<Product> findProductByName(String name) {
        return productRepository.findByName(name);
    }
     */


    /*
    public List<Product> getProductsByNameAndFilters(
        String name,
        String manufacturer
    ) {
        if (manufacturer != null) {
            System.out.println("Searching for products with name: " + name
                + " and manufacturer: " + manufacturer
            );
            return productRepository.findByNameStartingWithAndFilters(
                name,
                manufacturer
            );
        } else {
            System.out.println("Searching for products with name: " + name);
            return productRepository.findByNameIgnoreCaseStartingWith(name);
        }
    }
    */

}
