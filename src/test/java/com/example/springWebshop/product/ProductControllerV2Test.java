package com.example.springWebshop.product;

import com.example.springWebshop.controller.ProductController;
import com.example.springWebshop.dto.ProductDto;
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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ProductControllerV2Test {

    private MockMvc mockMvc;

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private ObjectMapper objectMapper;
    private List<ProductDto> productDtos;
    private ProductDto sampleProductDto;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();

        // Configure mockMvc with message converter to ensure content type is set
        mockMvc = MockMvcBuilders.standaloneSetup(productController)
            .setMessageConverters(new MappingJackson2HttpMessageConverter())
            .build();

        // Create DTOs for testing
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
        sampleProductDto = productDto1;  // Save one for individual tests
    }

    @Test
    public void testGetAllProducts() throws Exception {
        when(productService.getAllProducts()).thenReturn(productDtos);

        mockMvc.perform(get("/api/products"))
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

    @Test
    public void testGetProductById() throws Exception {
        when(productService.getProductDtoById(1L)).thenReturn(sampleProductDto);

        mockMvc.perform(get("/api/products/1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.productId", is(1)))
            .andExpect(jsonPath("$.productName", is("Smartphone")))
            .andExpect(jsonPath("$.productPrice", is(799.99)));
    }

    @Test
    public void testGetProductById_NotFound() throws Exception {
        when(productService.getProductDtoById(anyLong())).thenReturn(null);

        mockMvc.perform(get("/api/products/999"))
            .andExpect(status().isNotFound());
    }

    @Test
    public void testSearchProducts() throws Exception {
        List<ProductDto> searchResults = Arrays.asList(sampleProductDto);
        when(productService.searchProductsByName("Smart")).thenReturn(searchResults);

        mockMvc.perform(get("/api/products/search").param("query", "Smart"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].productId", is(1)))
            .andExpect(jsonPath("$[0].productName", is("Smartphone")));
    }

    @Test
    public void testSearchProducts_NoResults() throws Exception {
        when(productService.searchProductsByName(anyString())).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/products/search").param("query", "NonExistentProduct"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void testGetProductsByCategory() throws Exception {
        when(productService.getProductsByCategory(1L)).thenReturn(productDtos);

        mockMvc.perform(get("/api/products/category/1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].productId", is(1)))
            .andExpect(jsonPath("$[1].productId", is(2)));
    }

    @Test
    public void testGetProductsByCategory_EmptyCategory() throws Exception {
        when(productService.getProductsByCategory(anyLong())).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/products/category/999"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void testGetProductsByPriceRange() throws Exception {
        List<ProductDto> filteredProducts = Arrays.asList(sampleProductDto);
        when(productService.getProductsByPriceRange(500.0, 1000.0)).thenReturn(filteredProducts);

        mockMvc.perform(get("/api/products/price")
                .param("min", "500.0")
                .param("max", "1000.0"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].productId", is(1)))
            .andExpect(jsonPath("$[0].productPrice", is(799.99)));
    }

    @Test
    public void testGetProductsByPriceRange_MinOnly() throws Exception {
        when(productService.getProductsByPriceRange(eq(1000.0), isNull())).thenReturn(
            Arrays.asList(productDtos.get(1))
        );

        mockMvc.perform(get("/api/products/price")
                .param("min", "1000.0"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].productId", is(2)));
    }

    @Test
    public void testGetProductsByPriceRange_MaxOnly() throws Exception {
        when(productService.getProductsByPriceRange(isNull(), eq(1000.0))).thenReturn(
            Arrays.asList(productDtos.get(0))
        );

        mockMvc.perform(get("/api/products/price")
                .param("max", "1000.0"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].productId", is(1)));
    }

    @Test
    public void testGetProductsByPriceRange_NoParams() throws Exception {
        when(productService.getProductsByPriceRange(isNull(), isNull())).thenReturn(productDtos);

        mockMvc.perform(get("/api/products/price"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(2)));
    }
}
