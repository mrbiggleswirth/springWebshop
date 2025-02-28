package com.example.springWebshop.repository;

import com.example.springWebshop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// _____________________________________________________________________________

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    /**
     * Produces methods for CRUD, e.g.
     *   - findById(Long id)
     *   - findAll()
     */

    // Custom method generating query based on naming convention.
    List<Product> findByName(String name);
}
