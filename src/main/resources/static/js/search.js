document.addEventListener('DOMContentLoaded', function() {
    const searchForm = document.getElementById('search-form');
    const searchInput = document.getElementById('search-input');
    const searchResults = document.getElementById('search-results');
    const searchResultsInfo = document.getElementById('search-results-info');

    // Check if there's a query parameter for search
    const urlParams = new URLSearchParams(window.location.search);
    const query = urlParams.get('query');

    if (query) {
        searchInput.value = query;
        performSearch(query);
    }

    searchForm.addEventListener('submit', function(e) {
        e.preventDefault();
        const query = searchInput.value.trim();

        if (query) {
            // Update URL with search query
            const url = new URL(window.location);
            url.searchParams.set('query', query);
            window.history.pushState({}, '', url);

            performSearch(query);
        }
    });

    function performSearch(query) {
        searchResults.innerHTML = '<div class="loading">Searching...</div>';
        searchResultsInfo.textContent = '';

        fetch(`/api/products/search?query=${encodeURIComponent(query)}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(products => {
                displaySearchResults(products, query);
            })
            .catch(error => {
                searchResults.innerHTML = `<div class="error">Error performing search: ${error.message}</div>`;
                console.error('Error searching products:', error);
            });
    }

    function displaySearchResults(products, query) {
        if (!products || products.length === 0) {
            searchResultsInfo.textContent = `No products found for "${query}"`;
            searchResults.innerHTML = '<div class="no-results">No matching products found. Try a different search term.</div>';
            return;
        }

        searchResultsInfo.textContent = `Found ${products.length} product(s) for "${query}"`;
        searchResults.innerHTML = '';

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

            searchResults.appendChild(productCard);
        });
    }
});
