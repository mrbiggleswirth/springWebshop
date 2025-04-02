document.addEventListener('DOMContentLoaded', function() {
    // DOM elements
    const productsContainer = document.getElementById('products-container');
    const categorySelect = document.getElementById('category');
    const minPriceInput = document.getElementById('min-price');
    const maxPriceInput = document.getElementById('max-price');
    const filterPriceBtn = document.getElementById('filter-price-btn');

    // Load all products initially
    loadProducts();

    // Load categories for the dropdown
    loadCategories();

    // Event listeners
    categorySelect.addEventListener('change', function() {
        const categoryId = this.value;
        if (categoryId) {
            loadProductsByCategory(categoryId);
        } else {
            loadProducts();
        }
    });

    filterPriceBtn.addEventListener('click', function() {
        const minPrice = minPriceInput.value;
        const maxPrice = maxPriceInput.value;

        if (minPrice || maxPrice) {
            loadProductsByPriceRange(minPrice, maxPrice);
        } else {
            loadProducts();
        }
    });

    // Functions
    function loadProducts() {
        productsContainer.innerHTML = '<div class="loading">Loading products...</div>';

        fetch('/api/products')
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(products => {
                displayProducts(products);
            })
            .catch(error => {
                productsContainer.innerHTML = `<div class="error">Error loading products: ${error.message}</div>`;
                console.error('Error loading products:', error);
            });
    }

    function loadProductsByCategory(categoryId) {
        productsContainer.innerHTML = '<div class="loading">Loading products...</div>';

        fetch(`/api/products/category/${categoryId}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(products => {
                displayProducts(products);
            })
            .catch(error => {
                productsContainer.innerHTML = `<div class="error">Error loading products: ${error.message}</div>`;
                console.error('Error loading products by category:', error);
            });
    }

    function loadProductsByPriceRange(min, max) {
        productsContainer.innerHTML = '<div class="loading">Loading products...</div>';

        let url = '/api/products/price?';
        if (min) url += `min=${min}&`;
        if (max) url += `max=${max}`;

        fetch(url)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(products => {
                displayProducts(products);
            })
            .catch(error => {
                productsContainer.innerHTML = `<div class="error">Error loading products: ${error.message}</div>`;
                console.error('Error loading products by price range:', error);
            });
    }

    function loadCategories() {
        // Note: You'll need to implement a categories endpoint in your API
        // This is just a placeholder that you'll update when you add category endpoints

        // For now, we can add some example categories
        const exampleCategories = [
            { id: 1, name: 'Electronics' },
            { id: 2, name: 'Clothing' },
            { id: 3, name: 'Books' }
        ];

        exampleCategories.forEach(category => {
            const option = document.createElement('option');
            option.value = category.id;
            option.textContent = category.name;
            categorySelect.appendChild(option);
        });
    }

    function displayProducts(products) {
        if (!products || products.length === 0) {
            productsContainer.innerHTML = '<div class="no-products">No products found.</div>';
            return;
        }

        productsContainer.innerHTML = '';

        products.forEach(product => {
            const productCard = document.createElement('div');
            productCard.className = 'product-card';

            const stockStatus = product.productStockQuantity > 0
                ? `<span class="in-stock">In Stock (${product.productStockQuantity})</span>`
                : '<span class="out-of-stock">Out of Stock</span>';

            productCard.innerHTML = `
                <div class="product-image">
                    <img src="${product.productImageUrl || 'https://via.placeholder.com/300x200'}" alt="${product.productName}">
                </div>
                <div class="product-info">
                    <h3 class="product-title">${product.productName}</h3>
                    <div class="product-category">${product.productCategory || 'Uncategorized'}</div>
                    <div class="product-price">$${product.productPrice.toFixed(2)}</div>
                    <div class="product-stock">${stockStatus}</div>
                    <a href="product.html?id=${product.productId}" class="btn-details">View Details</a>
                </div>
            `;

            // Add click event to make the whole card clickable
            productCard.addEventListener('click', function() {
                window.location.href = `product.html?id=${product.productId}`;
            });

            productsContainer.appendChild(productCard);
        });
    }
});
