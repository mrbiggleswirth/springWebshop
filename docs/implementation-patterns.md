# Implementation Patterns in Spring Webshop

This document describes key implementation patterns used in the Spring Webshop project, particularly focusing on the transition from entity-based to DTO-based API design.

## Entity-Based vs DTO-Based Approach

### Entity-Based Approach (Original Implementation)

Our initial implementation directly returned JPA entities from controllers:

```java
@GetMapping("/products")
public List<Product> getAllProducts() {
    return productService.getAllProducts();
}