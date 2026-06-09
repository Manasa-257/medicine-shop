package com.medicine.online_medicine_shop.service;

import com.medicine.online_medicine_shop.dto.OrderRequest;
import com.medicine.online_medicine_shop.model.*;
import com.medicine.online_medicine_shop.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MedicineRepository medicineRepository;

    // Place Order
    @Transactional
        public Order placeOrder(OrderRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElse(null);
        if (user == null) return null;

        // Get cart items
        List<Cart> cartItems = cartRepository
                .findByUserUserId(request.getUserId());
        if (cartItems.isEmpty()) return null;

        // Calculate total amount
        double totalAmount = 0;
        for (Cart cart : cartItems) {
            totalAmount += cart.getMedicine().getPrice() * cart.getQuantity();
        }

        // Create order
        Order order = new Order();
        order.setUser(user);
        order.setTotalAmount(totalAmount);
        order.setDeliveryAddress(request.getDeliveryAddress());
        order.setStatus(Order.OrderStatus.PENDING);
        Order savedOrder = orderRepository.save(order);

        // Create order items
        for (Cart cart : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(savedOrder);
            orderItem.setMedicine(cart.getMedicine());
            orderItem.setQuantity(cart.getQuantity());
            orderItem.setPrice(cart.getMedicine().getPrice());
            orderItemRepository.save(orderItem);

            // Update stock
            Medicine medicine = cart.getMedicine();
            medicine.setStock(medicine.getStock() - cart.getQuantity());
            medicineRepository.save(medicine);
        }

        // Clear cart after order
        cartRepository.deleteByUserUserId(request.getUserId());

        return savedOrder;
    }

    // Get Orders by User
    public List<Order> getOrdersByUser(Integer userId) {
        return orderRepository.findByUserUserId(userId);
    }

    // Get Order by ID
    public Order getOrderById(Integer orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }

    // Get All Orders (Admin)
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // Update Order Status (Admin)
    public Order updateOrderStatus(Integer orderId, String status) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order == null) return null;
        order.setStatus(Order.OrderStatus.valueOf(status));
        return orderRepository.save(order);
    }

    // Cancel Order
    public String cancelOrder(Integer orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order == null) return "Order not found!";
        order.setStatus(Order.OrderStatus.CANCELLED);
        orderRepository.save(order);
        return "Order cancelled successfully!";
    }
}