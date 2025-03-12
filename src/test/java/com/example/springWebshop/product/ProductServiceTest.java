package com.example.springWebshop.product;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.springWebshop.model.Product;
import com.example.springWebshop.repository.ProductRepository;
import com.example.springWebshop.service.ProductService;

// _____________________________________________________________________________

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product sampleProduct;

    @BeforeEach
    void setUp() {
        // sampleProduct = new Product("Laptop", 1200.00);
        sampleProduct.setName("Laptop");
        sampleProduct.setPrice(new BigDecimal("1200.00"));
    }

    @Test
    void testFindProductByName() {
        // Arrange
        when(productRepository.findByName("Laptop"))
            .thenReturn(Optional.of(sampleProduct));

        // Act
        Optional<Product> result = productService.findProductByName("Laptop");

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Laptop", result.get().getName());
    }
}
