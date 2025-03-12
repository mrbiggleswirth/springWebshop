package com.example.springWebshop.product;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.example.springWebshop.model.Product;
import com.example.springWebshop.repository.ProductRepository;

// _____________________________________________________________________________

@DataJpaTest
public class ProductEntityTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void shouldSaveAndRetrieveProduct() {
        // Create a new product
        Product product = new Product();
        product.setName("Test Product");
        product.setDescription("This is a test product");
        product.setPrice(new BigDecimal("99.99"));
        product.setStockQuantity(10);
        product.setAvailable(true);
        product.setImageUrl("http://example.com/image.jpg");

        // Save the product
        Product savedProduct = entityManager.persistAndFlush(product);

        // Retrieve the product from the repository
        Product foundProduct = productRepository.findById(savedProduct.getId()).orElse(null);

        // Verify the product was retrieved correctly
        assertNotNull(foundProduct, "Product should be found");
        assertEquals("Test Product", foundProduct.getName());
        assertEquals("This is a test product", foundProduct.getDescription());
        assertEquals(0, new BigDecimal("99.99").compareTo(foundProduct.getPrice()));
        assertEquals(10, foundProduct.getStockQuantity());
        assertTrue(foundProduct.isAvailable());
        assertEquals("http://example.com/image.jpg", foundProduct.getImageUrl());

        // Verify timestamps were created
        assertNotNull(foundProduct.getCreatedAt());
        assertNotNull(foundProduct.getUpdatedAt());
    }
}
