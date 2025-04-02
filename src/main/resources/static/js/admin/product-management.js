document.addEventListener('DOMContentLoaded', function() {
    // Check authentication
    const token = localStorage.getItem('adminToken');
    if (!token) {
        window.location.href = 'login.html';
        return;
    }

    // DOM elements
    const productsTableBody = document.getElementById('products-table-body');
    const addProductBtn = document.getElementById('add-product-btn');
    const productModal = document.getElementById('product-modal');
    const confirmModal = document.getElementById('confirm-modal');
    const productForm = document.getElementById('product-form');
    const modalTitle = document.getElementById('modal-title');
    const closeModalBtns = document.querySelectorAll('.close-modal, .close-modal-btn');
    const logoutBtn = document.getElementById('logout-btn');
    const confirmDeleteBtn = document.getElementById('confirm-delete-btn');

    // Product form fields
    const productIdInput = document.getElementById('product-id');
    const productNameInput = document.getElementById('product-name');
    const productDescriptionInput = document.getElementById('product-description');
    const productPriceInput = document.getElementById('product-price');
    const productCategoryInput = document.getElementById('product-category');
    const productImageUrlInput = document.getElementById('product-image-url');
    const productStockInput = document.getElementById('product-stock');
    const productAvailableInput = document.getElementById('product-available');

    let deleteProductId = null;

    // Load products
    loadProducts();

    // Event listeners
    addProductBtn.addEventListener('click', function() {
        openAddProductModal();
    });

    productForm.addEventListener('submit', function(e) {
        e.preventDefault();
        saveProduct();
    });

    closeModalBtns.forEach(btn => {
        btn.addEventListener('click', function() {
            closeModal(productModal);
            closeModal(confirmModal);
        });
    });

    confirmDeleteBtn.addEventListener('click', function() {
        if (deleteProductId) {
            deleteProduct(deleteProductId);
        }
    });

    logoutBtn.addEventListener('click', function(e) {
        e.preventDefault();
        logout();
    });

    // Functions
    function loadProducts() {
        productsTableBody.innerHTML = '<tr><td colspan="8" class="loading">Loading products...</td></tr>';

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
                productsTableBody.innerHTML = `<tr><td colspan="8" class="error">Error loading products: ${error.message}</td></tr>`;
                console.error('Error loading products:', error);
            });
    }

    function displayProducts(products) {
        if (!products || products.length === 0) {
            productsTableBody.innerHTML = '<tr><td colspan="8">No products found</td></tr>';
            return;
        }

        productsTableBody.innerHTML = '';

        products.forEach(product => {
            const row = document.createElement('tr');

            const stockDisplay = product.productStockQuantity === 0
                ? '<span class="out-of-stock">Out of stock</span>'
                : (product.productStockQuantity < 10
                    ? `<span class="low-stock">${product.productStockQuantity}</span>`
                    : product.productStockQuantity);

            const availableDisplay = product.productIsAvailable
                ? '<span class="status-badge status-available">Available</span>'
                : '<span class="status-badge status-unavailable">Unavailable</span>';

            row.innerHTML = `
                <td>${product.productId}</td>
                <td><img src="${product.productImageUrl || 'https://via.placeholder.com/60x60'}" alt="${product.productName}" class="product-image-small"></td>
                <td>${product.productName}</td>
                <td>${product.productCategory || 'Uncategorized'}</td>
                <td>$${product.productPrice.toFixed(2)}</td>
                <td>${stockDisplay}</td>
                <td>${availableDisplay}</td>
                <td class="action-buttons">
                    <button class="edit-btn" data-id="${product.productId}">Edit</button>
                    <button class="toggle-btn" data-id="${product.productId}">${product.productIsAvailable ? 'Disable' : 'Enable'}</button>
                    <button class="delete-btn" data-id="${product.productId}">Delete</button>
                </td>
            `;

            // Add event listeners to action buttons
            const editBtn = row.querySelector('.edit-btn');
            const toggleBtn = row.querySelector('.toggle-btn');
            const deleteBtn = row.querySelector('.delete-btn');

            editBtn.addEventListener('click', function() {
                openEditProductModal(product.productId);
            });

            toggleBtn.addEventListener('click', function() {
                toggleProductAvailability(product.productId);
            });

            deleteBtn.addEventListener('click', function() {
                openDeleteConfirmModal(product.productId);
            });

            productsTableBody.appendChild(row);
        });
    }

    function openAddProductModal() {
        // Reset form
        productForm.reset();
        productIdInput.value = '';
        modalTitle.textContent = 'Add New Product';

        // Show modal
        openModal(productModal);
    }

    function openEditProductModal(productId) {
        modalTitle.textContent = 'Edit Product';

        // Get product details
        fetch(`/api/products/${productId}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(product => {
                // Fill form with product data
                productIdInput.value = product.productId;
                productNameInput.value = product.productName;
                productDescriptionInput.value = product.productDescription || '';
                productPriceInput.value = product.productPrice;
                productCategoryInput.value = product.productCategory || '';
                productImageUrlInput.value = product.productImageUrl || '';
                productStockInput.value = product.productStockQuantity;
                productAvailableInput.checked = product.productIsAvailable;

                // Show modal
                openModal(productModal);
            })
            .catch(error => {
                alert(`Error loading product details: ${error.message}`);
                console.error('Error loading product details:', error);
            });
    }

    function saveProduct() {
        const productId = productIdInput.value;

        const productData = {
            productId: productId ? Number(productId) : null,
            productName: productNameInput.value,
            productDescription: productDescriptionInput.value,
            productPrice: Number(productPriceInput.value),
            productCategory: productCategoryInput.value,
            productImageUrl: productImageUrlInput.value,
            productStockQuantity: Number(productStockInput.value),
            productIsAvailable: productAvailableInput.checked
        };

        const url = productId
            ? `/api/admin/products/${productId}`
            : '/api/admin/products';

        const method = productId ? 'PUT' : 'POST';

        fetch(url, {
            method: method,
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            body: JSON.stringify(productData)
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(savedProduct => {
            closeModal(productModal);
            loadProducts();  // Refresh product list
            alert(productId ? 'Product updated successfully!' : 'Product created successfully!');
        })
        .catch(error => {
            alert(`Error saving product: ${error.message}`);
            console.error('Error saving product:', error);
        });
    }

    function toggleProductAvailability(productId) {
        fetch(`/api/admin/products/${productId}/availability`, {
            method: 'PATCH',
            headers: {
                'Authorization': `Bearer ${token}`
            }
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(updatedProduct => {
            loadProducts();  // Refresh product list
        })
        .catch(error => {
            alert(`Error toggling product availability: ${error.message}`);
            console.error('Error toggling product availability:', error);
        });
    }

    function openDeleteConfirmModal(productId) {
        deleteProductId = productId;
        openModal(confirmModal);
    }

    function deleteProduct(productId) {
        fetch(`/api/admin/products/${productId}`, {
            method: 'DELETE',
            headers: {
                'Authorization': `Bearer ${token}`
            }
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }

            closeModal(confirmModal);
            loadProducts();  // Refresh product list
            alert('Product deleted successfully!');
        })
        .catch(error => {
            closeModal(confirmModal);
            alert(`Error deleting product: ${error.message}`);
            console.error('Error deleting product:', error);
        });
    }

    function openModal(modal) {
        modal.style.display = 'block';
    }

    function closeModal(modal) {
        modal.style.display = 'none';
    }

    function logout() {
        localStorage.removeItem('adminToken');
        window.location.href = 'login.html';
    }

    // Close modal when clicking outside of it
    window.addEventListener('click', function(e) {
        if (e.target === productModal) {
            closeModal(productModal);
        } else if (e.target === confirmModal) {
            closeModal(confirmModal);
        }
    });
});
