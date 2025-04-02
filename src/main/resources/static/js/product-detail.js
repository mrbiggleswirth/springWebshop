document.addEventListener('DOMContentLoaded', function() {
    const productDetailContainer = document.getElementById('product-detail-container');

    // Get product ID from URL query parameter
    const urlParams = new URLSearchParams(window.location.search);
    const productId = urlParams.get('id');

    if (!productId) {
        productDetailContainer.innerHTML = '<div class="error">Product ID is missing. Please go back and select a product.</div>';
        return;
    }

    // Load product details
    loadProductDetails(productId);

    function loadProductDetails(id) {
        fetch(`/api/products/${id}`)
            .then(response => {
                if (!response.ok) {
                    if (response.status === 404) {
                        throw new Error('Product not found');
                    }
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(product => {
                displayProductDetails(product);
                // Update page title with product name
                document.title = `${product.productName} - Spring Webshop`;
            })
            .catch(error => {
                productDetailContainer.innerHTML = `<div class="error">Error loading product details: ${error.message}</div>`;
                console.error('Error loading product details:', error);
            });
    }

    function displayProductDetails(product) {
        const stockStatus = product.productStockQuantity > 0
            ? `<span class="in-stock">In Stock (${product.productStockQuantity} available)</span>`
            : '<span class="out-of-stock">Out of Stock</span>';

        productDetailContainer.innerHTML = `
            <div class="product-detail">
                <div class="product-detail-image">
                    <img src="${product.productImageUrl || 'https://via.placeholder.com/600x400'}" alt="${product.productName}">
                </div>
                <div class="product-detail-info">
                    <h2 class="product-detail-title">${product.productName}</h2>
                    <div class="product-detail-category">${product.productCategory || 'Uncategorized'}</div>
                    <div class="product-detail-price">$${product.productPrice.toFixed(2)}</div>
                    <div class="product-detail-description">${product.productDescription || 'No description available.'}</div>
                    <div class="product-detail-stock">${stockStatus}</div>
                </div>
            </div>
        `;
    }
});
