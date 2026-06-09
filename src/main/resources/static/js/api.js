const BASE_URL = 'http://localhost:8080/api';

const API = {
    // Auth
    register: (data) => fetch(`${BASE_URL}/auth/register`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data)
    }),

    login: (data) => fetch(`${BASE_URL}/auth/login`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data)
    }),

    // Medicines
    getMedicines: () => fetch(`${BASE_URL}/medicines`),
    searchMedicines: (name) => fetch(`${BASE_URL}/medicines/search?name=${name}`),
    getMedicinesByCategory: (id) => fetch(`${BASE_URL}/medicines/category/${id}`),
    getCategories: () => fetch(`${BASE_URL}/medicines/categories`),

    // Cart
    addToCart: (data) => fetch(`${BASE_URL}/cart/add`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data)
    }),
    getCart: (userId) => fetch(`${BASE_URL}/cart/${userId}`),
    updateCart: (cartId, quantity) => fetch(`${BASE_URL}/cart/${cartId}?quantity=${quantity}`, {
        method: 'PUT'
    }),
    removeFromCart: (cartId) => fetch(`${BASE_URL}/cart/${cartId}`, {
        method: 'DELETE'
    }),

    // Orders
    placeOrder: (data) => fetch(`${BASE_URL}/orders/place`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data)
    }),
    getOrders: (userId) => fetch(`${BASE_URL}/orders/user/${userId}`)
};

// Auth Helpers
const Auth = {
    getUser: () => JSON.parse(localStorage.getItem('user')),
    getToken: () => localStorage.getItem('token'),
    isLoggedIn: () => !!localStorage.getItem('token'),
    logout: () => {
        localStorage.removeItem('user');
        localStorage.removeItem('token');
        window.location.href = 'login.html';
    }
};