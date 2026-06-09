function loadCart() {
    const userId = localStorage.getItem('userId');
    if (!userId) {
        document.getElementById('cartItems').innerHTML = `
            <div class="empty-state">
                <h3>Please login to view cart</h3>
                <a href="login.html" class="btn-primary"
                    style="display:inline-block;margin-top:10px;">Login</a>
            </div>`;
        return;
    }

    API.getCart(userId)
        .then(res => res.json())
        .then(items => renderCart(items));
}

function renderCart(items) {
    const container = document.getElementById('cartItems');
    if (items.length === 0) {
        container.innerHTML = `
            <div class="empty-state">
                <h3>Your cart is empty</h3>
                <a href="medicines.html">Browse Medicines</a>
            </div>`;
        return;
    }

    let subtotal = 0;
    container.innerHTML = items.map(item => {
        subtotal += item.medicine.price * item.quantity;
        return `
        <div class="cart-item">
            <img src="${item.medicine.imageUrl ||
                'https://via.placeholder.com/80?text=Med'}"
                alt="${item.medicine.name}">
            <div class="item-info">
                <h3>${item.medicine.name}</h3>
                <p>₹${item.medicine.price}</p>
            </div>
            <div class="quantity-controls">
                <button onclick="updateQty(${item.cartId},
                    ${item.quantity - 1})">-</button>
                <span>${item.quantity}</span>
                <button onclick="updateQty(${item.cartId},
                    ${item.quantity + 1})">+</button>
            </div>
            <button class="btn-danger"
                onclick="removeItem(${item.cartId})">Remove</button>
        </div>`;
    }).join('');

    document.getElementById('cartSummary').style.display = 'block';
    document.getElementById('subtotal').textContent = `₹${subtotal}`;
    document.getElementById('total').textContent = `₹${subtotal + 50}`;
}

function updateQty(cartId, quantity) {
    if (quantity < 1) return;
    API.updateCart(cartId, quantity)
        .then(() => loadCart());
}

function removeItem(cartId) {
    API.removeFromCart(cartId)
        .then(() => loadCart());
}

function placeOrder() {
    const userId = localStorage.getItem('userId');
    const deliveryAddress = document.getElementById('deliveryAddress').value;

    if (!deliveryAddress) {
        alert('Please enter delivery address!');
        return;
    }

    API.placeOrder({ userId: parseInt(userId), deliveryAddress,
        paymentMethod: 'Cash on Delivery' })
        .then(res => res.json())
        .then(data => {
            alert('Order placed successfully! 🎉');
            window.location.href = 'orders.html';
        });
}