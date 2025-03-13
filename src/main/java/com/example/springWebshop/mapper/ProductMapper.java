package com.example.springWebshop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.example.springWebshop.dto.ProductDto;
import com.example.springWebshop.model.Product;

// _____________________________________________________________________________

@Mapper
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(source = "id", target = "productId")
    @Mapping(source = "name", target = "productName")
    @Mapping(source = "description", target = "productDescription")
    @Mapping(source = "price", target = "productPrice")
    @Mapping(source = "category.name", target = "productCategory")
    @Mapping(source = "imageUrl", target = "productImageUrl")
    @Mapping(source = "stockQuantity", target = "productStockQuantity")
    @Mapping(source = "isAvailable", target = "productIsAvailable")
    ProductDto productToProductDto(Product product);
}
