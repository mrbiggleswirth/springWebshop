# Entity-Based Implementation Examples

This folder contains examples of the original entity-based implementation patterns
used in the Spring Webshop project before migrating to DTOs.

## Advantages of Entity-Based Approach

- Simpler implementation with fewer mapping layers
- Less code to maintain initially
- Direct access to all entity properties

## Disadvantages of Entity-Based Approach

- Exposes internal domain model to API clients
- Can cause circular reference issues with JSON serialization
- Tightly couples the API contract to the database schema
- Changes to entities impact API clients directly

## Migration to DTO-Based Approach

The project transitioned to a DTO-based approach to:
1. Decouple the API from the internal domain model
2. Provide better control over what data is exposed
3. Allow the domain model to evolve independently
4. Avoid common serialization issues

See the main codebase for the current DTO-based implementation.
