package com.example.springWebshop.product;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.example.springWebshop.controller.ProductController;
import com.example.springWebshop.dto.ProductDto;
import com.example.springWebshop.service.ProductService;

// _____________________________________________________________________________

@WebMvcTest(ProductController.class)
@ContextConfiguration(classes = ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

// _____________________________________________________________________________

    @Test
    public void testGetAllProducts() throws Exception {
        List<ProductDto> productList = List.of(
            new ProductDto(1L, "Laptop", "High-end gaming laptop", new BigDecimal("1500.00"), "Electronics", "url1", 10, true),
            new ProductDto(2L, "Phone", "Latest smartphone", new BigDecimal("800.00"), "Electronics", "url2", 5, true)
        );

        when(productService.getAllProducts()).thenReturn(productList);

        mockMvc.perform(get("/products"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.size()").value(2))
            .andExpect(jsonPath("$[0].productName").value("Laptop"))
            .andExpect(jsonPath("$[1].productName").value("Phone"))
            .andDo(print());
    }

// _____________________________________________________________________________

    @Test
    public void testGetProductById() throws Exception {
        ProductDto productDto = new ProductDto(1L, "Laptop", "High-end gaming laptop", new BigDecimal("1500.00"), "Electronics", "url1", 10, true);

        // Mock the service method to return ProductDto.
        when(productService.getProductById(1L)).thenReturn(productDto);

        // Perform the GET request and verify response.
        mockMvc.perform(get("/product/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.productId").value(1))
            .andExpect(jsonPath("$.productName").value("Laptop"))
            .andExpect(jsonPath("$.productPrice").value(1500.00))
            .andDo(print());
    }

// _____________________________________________________________________________

    @Test
    public void testGetProductsByName() throws Exception {
        List<ProductDto> productList = List.of(
            new ProductDto(1L, "Laptop", "High-end gaming laptop", new BigDecimal("1500.00"), "Electronics", "url1", 10, true)
        );

        when(productService.getProductsByNameIgnoreCaseStartingWith("Lap")).thenReturn(productList);

        mockMvc.perform(get("/products/name/Lap"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.size()").value(1))
            .andExpect(jsonPath("$[0].productName").value("Laptop"))
            .andDo(print());
    }
}
