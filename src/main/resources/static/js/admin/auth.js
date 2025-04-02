document.addEventListener('DOMContentLoaded', function() {
    const loginForm = document.getElementById('login-form');
    const errorMessage = document.getElementById('error-message');

    // Check if already logged in
    const token = localStorage.getItem('adminToken');
    if (token) {
        // Redirect to admin products page
        window.location.href = 'products.html';
        return;
    }

    loginForm.addEventListener('submit', function(e) {
        e.preventDefault();

        const email = document.getElementById('email').value;
        const password = document.getElementById('password').value;

        // Clear previous error
        errorMessage.textContent = '';

        // Perform login
        fetch('/auth/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ email, password })
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Invalid credentials');
            }
            return response.json();
        })
        .then(data => {
            // Save token to localStorage
            localStorage.setItem('adminToken', data.token);

            // Redirect to admin products page
            window.location.href = 'products.html';
        })
        .catch(error => {
            errorMessage.textContent = error.message;
            console.error('Login error:', error);
        });
    });
});
