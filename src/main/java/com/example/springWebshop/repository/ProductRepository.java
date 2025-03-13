package com.example.springWebshop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
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


    /**
     * Find products by name starting with the given string.
     */
    // List<Product> findByNameStartingWith(String name);
    // List<Product> findByNameIgnoreCase(String name);
    // List<Product> findByNameIgnoreCaseContaining(String name);
    List<Product> findByNameIgnoreCaseStartingWith(String name);

    /**
     * This generic naming might be causing server errors!
     */
    // List<Product> findByNameStartingWithAndFilters(String name, String manufacturer);

// _____________________________________________________________________________
// For testing
    // Optional<Product> findByName(String name);

}
