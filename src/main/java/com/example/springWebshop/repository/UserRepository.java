package com.example.springWebshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springWebshop.model.User;

// _____________________________________________________________________________

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Produces methods for CRUD, e.g.
     *   - findById(Long id)
     *   - findAll()
     */

    // Custom method generating query based on naming convention.
    User findByEmail(String email);
}
