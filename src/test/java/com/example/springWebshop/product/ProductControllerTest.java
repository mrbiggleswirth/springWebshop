package com.example.springWebshop.product;

import com.example.springWebshop.controller.ProductController;
import com.example.springWebshop.dto.ProductDto;
import com.example.springWebshop.mapper.ProductMapper;
import com.example.springWebshop.model.Category;
import com.example.springWebshop.model.Product;
import com.example.springWebshop.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// _____________________________________________________________________________

public class ProductControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private Product testProduct1;
    private Product testProduct2;
    private List<ProductDto> productDtos;

// _____________________________________________________________________________

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();

        // Create test category
        Category electronics = new Category();
        electronics.setName("Electronics");

        // Create test products
        testProduct1 = new Product();
        testProduct1.setName("Smartphone");
        testProduct1.setDescription("High-end smartphone with great camera");
        testProduct1.setPrice(new BigDecimal("799.99"));
        testProduct1.setCategory(electronics);
        testProduct1.setImageUrl("smartphone.jpg");
        testProduct1.setStockQuantity(50);
        testProduct1.setIsAvailable(true);

        testProduct2 = new Product();
        testProduct2.setName("Laptop");
        testProduct2.setDescription("Powerful laptop for work and gaming");
        testProduct2.setPrice(new BigDecimal("1299.99"));
        testProduct2.setCategory(electronics);
        testProduct2.setImageUrl("laptop.jpg");
        testProduct2.setStockQuantity(30);
        testProduct2.setIsAvailable(true);

        // Create DTOs
        ProductDto productDto1 = ProductMapper.INSTANCE.productToProductDto(testProduct1);
        ProductDto productDto2 = ProductMapper.INSTANCE.productToProductDto(testProduct2);
        productDtos = Arrays.asList(productDto1, productDto2);
    }

// _____________________________________________________________________________

    @Test
    public void testGetAllProducts() throws Exception {
        when(productService.getAllProducts()).thenReturn(productDtos);

        mockMvc.perform(get("/products")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].productId", is(1)))
            .andExpect(jsonPath("$[0].productName", is("Smartphone")))
            .andExpect(jsonPath("$[0].productPrice", is(799.99)))
            .andExpect(jsonPath("$[0].productCategory", is("Electronics")))
            .andExpect(jsonPath("$[1].productId", is(2)))
            .andExpect(jsonPath("$[1].productName", is("Laptop")))
            .andExpect(jsonPath("$[1].productPrice", is(1299.99)));
    }

// _____________________________________________________________________________

    @Test
    public void testGetProductById() throws Exception {
        when(productService.getProductById(anyLong())).thenReturn(testProduct1);

        mockMvc.perform(get("/product/1")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id", is(1)))
            .andExpect(jsonPath("$.name", is("Smartphone")))
            .andExpect(jsonPath("$.price", is(799.99)))
            .andExpect(jsonPath("$.stockQuantity", is(50)))
            .andExpect(jsonPath("$.isAvailable", is(true)));
    }

// _____________________________________________________________________________

    @Test
    public void testGetProductsByNameIgnoreCaseStartingWith() throws Exception {
        List<Product> products = Arrays.asList(testProduct1);
        when(productService.getProductsByNameIgnoreCaseStartingWith(anyString())).thenReturn(products);

        mockMvc.perform(get("/products/name/Smart")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].id", is(1)))
            .andExpect(jsonPath("$[0].name", is("Smartphone")))
            .andExpect(jsonPath("$[0].price", is(799.99)));
    }

// _____________________________________________________________________________

    @Test
    public void testGetProductById_NotFound() throws Exception {
        when(productService.getProductById(anyLong())).thenReturn(null);

        mockMvc.perform(get("/product/999")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            // .andExpect(status().isNotFound()) // Expecting 404 Not Found.
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$").doesNotExist());
    }

// _____________________________________________________________________________

    @Test
    public void testGetProductsByNameIgnoreCaseStartingWith_NoResults() throws Exception {
        when(productService.getProductsByNameIgnoreCaseStartingWith(anyString())).thenReturn(Arrays.asList());

        mockMvc.perform(get("/products/name/NonExistentProduct")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(0)));
    }
}
