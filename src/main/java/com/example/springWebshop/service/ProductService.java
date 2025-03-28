package com.example.springWebshop.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springWebshop.dto.ProductDto;
import com.example.springWebshop.mapper.ProductMapper;
import com.example.springWebshop.model.Category;
import com.example.springWebshop.model.Product;
import com.example.springWebshop.repository.CategoryRepository;
import com.example.springWebshop.repository.ProductRepository;

// _____________________________________________________________________________

/**
 * ProductService provides business logic for product-related operations.
 *
 * Implementation note:
 * This service originally returned entities directly:
 *
 * public List<Product> getProductsByNameIgnoreCaseStartingWith(String name) {
 *     return productRepository.findByNameIgnoreCaseStartingWith(name);
 * }
 *
 *
 * Now it uses DTOs for better API design:
 *
 * public List<ProductDto> searchProductsByName(String query) {
 *     List<Product> products = productRepository.findByNameIgnoreCaseStartingWith(query);
 *     return products.stream().map(ProductMapper.INSTANCE::productToProductDto).collect(Collectors.toList());
 * }
 */

@Service
public class ProductService {
    /**
     * Constructor injection is preferred over @Autowired on fields.
     */
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(
        ProductRepository productRepository,
        CategoryRepository categoryRepository
    ) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

// _____________________________________________________________________________
// Example of transitioning to DTO.

    /*
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
     */

    // Get all products as DTOs.
    public List<ProductDto> getAllProducts() {
        // Fetches Product entities.
        List<Product> products = productRepository.findAll();
        return products.stream()
            // Converts Product entities to ProductDto objects.
            .map(ProductMapper.INSTANCE::productToProductDto)
            .toList();
    }

// _____________________________________________________________________________
// Product ID

    // Get product by ID as entity, unsafe.
    public Product getProductById(Long id) {
        /**
         * Fetches product by ID or returns null.
         */
        return productRepository.findById(id).orElse(null);
    }

    // Get product by ID as DTO.
    public ProductDto getProductDtoById(Long id) {
        Product product = getProductById(id);
        if (product != null) {
            return ProductMapper.INSTANCE.productToProductDto(product);
        }
        return null;
    }

// _____________________________________________________________________________
// Searches 2

    // Search products by name.
    public List<ProductDto> searchProductsByName(String query) {
        List<Product> products = productRepository.findByNameIgnoreCaseStartingWith(query);
        return products.stream()
            .map(ProductMapper.INSTANCE::productToProductDto)
            .collect(Collectors.toList());
    }

// _____________________________________________________________________________
// Searches 1

    /*
    public List<Product> getProductsByNameIgnoreCaseStartingWith(String name) {
        System.out.println("Searching for products with name: " + name);
        return productRepository.findByNameIgnoreCaseStartingWith(name);
    }
    */

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

// _____________________________________________________________________________

    // Get products by category.
    public List<ProductDto> getProductsByCategory(Long categoryId) {
        // This method requires implementation of findByCategoryId in ProductRepository.
        // For now, we'll do filtering in memory.
        List<Product> products = productRepository.findAll().stream()
            .filter(product -> product.getCategory() != null &&
                product.getCategory().getId().equals(categoryId))
            .collect(Collectors.toList());

        return products.stream()
            .map(ProductMapper.INSTANCE::productToProductDto)
            .collect(Collectors.toList());
    }

// _____________________________________________________________________________

    // Get products by price range.
    public List<ProductDto> getProductsByPriceRange(Double min, Double max) {
        List<Product> products = productRepository.findAll().stream()
            .filter(product -> {
                if (min != null && max != null) {
                    return product.getPrice().compareTo(BigDecimal.valueOf(min)) >= 0 &&
                        product.getPrice().compareTo(BigDecimal.valueOf(max)) <= 0;
                } else if (min != null) {
                    return product.getPrice().compareTo(BigDecimal.valueOf(min)) >= 0;
                } else if (max != null) {
                    return product.getPrice().compareTo(BigDecimal.valueOf(max)) <= 0;
                }
                return true;
            })
            .collect(Collectors.toList());

        return products.stream()
            .map(ProductMapper.INSTANCE::productToProductDto)
            .collect(Collectors.toList());
    }

// _____________________________________________________________________________
// CRUD

    // Create a new product.
    @Transactional
    public ProductDto createProduct(ProductDto productDto) {
        Product product = new Product();
        updateProductFromDto(product, productDto);
        Product savedProduct = productRepository.save(product);
        return ProductMapper.INSTANCE.productToProductDto(savedProduct);
    }

// _____________________________________________________________________________
// CRUD

    // Update an existing product.
    @Transactional
    public ProductDto updateProduct(Long id, ProductDto productDto) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            updateProductFromDto(product, productDto);
            Product savedProduct = productRepository.save(product);
            return ProductMapper.INSTANCE.productToProductDto(savedProduct);
        }
        return null;
    }

// _____________________________________________________________________________
// CRUD

    // Delete a product.
    @Transactional
    public boolean deleteProduct(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }

// _____________________________________________________________________________

    // Update stock quantity.
    @Transactional
    public ProductDto updateStockQuantity(Long id, Integer quantity) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setStockQuantity(quantity);
            Product savedProduct = productRepository.save(product);
            return ProductMapper.INSTANCE.productToProductDto(savedProduct);
        }
        return null;
    }

// _____________________________________________________________________________

    // Toggle product availability.
    @Transactional
    public ProductDto toggleProductAvailability(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setIsAvailable(!product.getIsAvailable());
            Product savedProduct = productRepository.save(product);
            return ProductMapper.INSTANCE.productToProductDto(savedProduct);
        }
        return null;
    }

// _____________________________________________________________________________

    // Save a product entity.
    @Transactional
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

// _____________________________________________________________________________

    // Helper method to update product from DTO.
    private void updateProductFromDto(Product product, ProductDto productDto) {
        product.setName(productDto.getProductName());
        product.setDescription(productDto.getProductDescription());
        product.setPrice(productDto.getProductPrice());
        product.setImageUrl(productDto.getProductImageUrl());
        product.setStockQuantity(productDto.getProductStockQuantity());
        product.setIsAvailable(productDto.getProductIsAvailable());

        // Set category if provided
        if (productDto.getProductCategory() != null) {
            // This assumes you have a method to find category by name
            // If not, you'll need to modify this logic
            // For example, you might need to use a categoryId instead
            Optional<Category> category = categoryRepository.findByName(productDto.getProductCategory());
            category.ifPresent(product::setCategory);
        }
    }
}
