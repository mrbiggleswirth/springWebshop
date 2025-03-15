package com.example.springWebshop.product;

import com.example.springWebshop.controller.ProductController;
import com.example.springWebshop.dto.ProductDto;
import com.example.springWebshop.model.Category;
import com.example.springWebshop.model.Product;
import com.example.springWebshop.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// _____________________________________________________________________________

public class ProductControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private ObjectMapper objectMapper;
    private List<ProductDto> productDtos;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();

        // Configure mockMvc with message converter to ensure content type is set
        mockMvc = MockMvcBuilders.standaloneSetup(productController)
            .setMessageConverters(new MappingJackson2HttpMessageConverter())
            .build();

        // Create DTOs directly since we can't set IDs on entities
        ProductDto productDto1 = new ProductDto();
        productDto1.setProductId(1L);
        productDto1.setProductName("Smartphone");
        productDto1.setProductDescription("High-end smartphone with great camera");
        productDto1.setProductPrice(new BigDecimal("799.99"));
        productDto1.setProductCategory("Electronics");
        productDto1.setProductImageUrl("smartphone.jpg");
        productDto1.setProductStockQuantity(50);
        productDto1.setProductIsAvailable(true);

        ProductDto productDto2 = new ProductDto();
        productDto2.setProductId(2L);
        productDto2.setProductName("Laptop");
        productDto2.setProductDescription("Powerful laptop for work and gaming");
        productDto2.setProductPrice(new BigDecimal("1299.99"));
        productDto2.setProductCategory("Electronics");
        productDto2.setProductImageUrl("laptop.jpg");
        productDto2.setProductStockQuantity(30);
        productDto2.setProductIsAvailable(true);

        productDtos = Arrays.asList(productDto1, productDto2);
    }

// _____________________________________________________________________________
// 1
    @Test
    public void testGetAllProducts() throws Exception {
        when(productService.getAllProducts()).thenReturn(productDtos);

        mockMvc.perform(get("/products"))
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
// 2
    @Test
    public void testGetProductById() throws Exception {
        // Create product directly in the test with reflection or mock approach
        Product mockProduct = mock(Product.class);
        when(mockProduct.getId()).thenReturn(1L);
        when(mockProduct.getName()).thenReturn("Smartphone");
        when(mockProduct.getDescription()).thenReturn("High-end smartphone with great camera");
        when(mockProduct.getPrice()).thenReturn(new BigDecimal("799.99"));
        when(mockProduct.getStockQuantity()).thenReturn(50);
        when(mockProduct.getIsAvailable()).thenReturn(true);

        when(productService.getProductById(anyLong())).thenReturn(mockProduct);

        mockMvc.perform(get("/product/1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id", is(1)))
            .andExpect(jsonPath("$.name", is("Smartphone")))
            .andExpect(jsonPath("$.price", is(799.99)))
            .andExpect(jsonPath("$.stockQuantity", is(50)))
            .andExpect(jsonPath("$.isAvailable", is(true)));
    }

// _____________________________________________________________________________
// 3
    @Test
    public void testGetProductsByNameIgnoreCaseStartingWith() throws Exception {
        // Create product directly in the test with reflection or mock approach
        Product mockProduct = mock(Product.class);
        when(mockProduct.getId()).thenReturn(1L);
        when(mockProduct.getName()).thenReturn("Smartphone");
        when(mockProduct.getPrice()).thenReturn(new BigDecimal("799.99"));

        List<Product> products = Arrays.asList(mockProduct);
        when(productService.getProductsByNameIgnoreCaseStartingWith(anyString())).thenReturn(products);

        mockMvc.perform(get("/products/name/Smart"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].id", is(1)))
            .andExpect(jsonPath("$[0].name", is("Smartphone")))
            .andExpect(jsonPath("$[0].price", is(799.99)));
    }

// _____________________________________________________________________________
// 4
    @Test
    public void testGetProductById_NotFound() throws Exception {
        when(productService.getProductById(anyLong())).thenReturn(null);

        mockMvc.perform(get("/product/999"))
            .andExpect(status().isOk())
            // Don't check content type when the response is empty
            .andExpect(jsonPath("$").doesNotExist());
    }

// _____________________________________________________________________________
// 5
    @Test
    public void testGetProductsByNameIgnoreCaseStartingWith_NoResults() throws Exception {
        when(productService.getProductsByNameIgnoreCaseStartingWith(anyString())).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/products/name/NonExistentProduct"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(0)));
    }
}
