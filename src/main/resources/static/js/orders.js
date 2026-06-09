function loadOrders() {
    const userId = localStorage.getItem('userId');
    if (!userId) {
        document.getElementById('ordersContainer').innerHTML = `
            <div class="empty-state">
                <h3>Please login to view orders</h3>
                <a href="login.html">Login here</a>
            </div>`;
        return;
    }

    API.getOrders(userId)
        .then(res => res.json())
        .then(orders => renderOrders(orders));
}

function renderOrders(orders) {
    const container = document.getElementById('ordersContainer');
    if (orders.length === 0) {
        container.innerHTML = `
            <div class="empty-state">
                <h3>No orders yet</h3>
                <a href="medicines.html">Start Shopping</a>
            </div>`;
        return;
    }

    container.innerHTML = orders.map(order => `
        <div class="order-card">
            <div class="order-header">
                <div>
                    <h3>Order #${order.orderId}</h3>
                    <p style="color:#6b7280;font-size:13px;">
                        ${new Date(order.orderDate).toLocaleDateString()}
                    </p>
                </div>
                <span class="status-badge status-${order.status}">
                    ${order.status}
                </span>
            </div>
            <div class="summary-row">
                <span>Delivery Address</span>
                <span>${order.deliveryAddress}</span>
            </div>
            <div class="summary-row total">
                <span>Total Amount</span>
                <span>₹${order.totalAmount}</span>
            </div>
        </div>
    `).join('');
}