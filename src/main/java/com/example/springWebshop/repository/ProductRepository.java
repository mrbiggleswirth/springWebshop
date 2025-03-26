package com.example.springWebshop.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.springWebshop.model.Product;

// _____________________________________________________________________________

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    /**
     * Produces methods for CRUD, e.g.
     *   - findById(Long id)
     *   - findAll()
     */

// _____________________________________________________________________________

    /**
     * Find products by name starting with the given string.
     */
    // List<Product> findByNameStartingWith(String name);
    // List<Product> findByNameIgnoreCase(String name);
    // List<Product> findByNameIgnoreCaseContaining(String name);
    List<Product> findByNameIgnoreCaseStartingWith(String name);

// _____________________________________________________________________________

    /**
     * This generic naming might be causing server errors!
     */
    // List<Product> findByNameStartingWithAndFilters(String name, String manufacturer);

// _____________________________________________________________________________
// For testing
    // Optional<Product> findByName(String name);

// _____________________________________________________________________________
// Additional methods for the MVP.

    List<Product> findByCategoryId(Long categoryId);

    List<Product> findByPriceBetween(BigDecimal min, BigDecimal max);

// _____________________________________________________________________________

    @Query("SELECT p FROM Product p WHERE p.stockQuantity > 0 AND p.isAvailable = true")
    List<Product> findAvailableProducts();

    @Query("SELECT p FROM Product p WHERE p.name LIKE %:keyword% OR p.description LIKE %:keyword%")
    List<Product> searchByKeyword(String keyword);
}
