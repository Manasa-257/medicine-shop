package com.medicine.online_medicine_shop.service;

import com.medicine.online_medicine_shop.model.Medicine;
import com.medicine.online_medicine_shop.model.Order;
import com.medicine.online_medicine_shop.model.User;
import com.medicine.online_medicine_shop.repository.MedicineRepository;
import com.medicine.online_medicine_shop.repository.OrderRepository;
import com.medicine.online_medicine_shop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MedicineRepository medicineRepository;

    @Autowired
    private OrderRepository orderRepository;

    // Get Dashboard Stats
    public Map<String, Object> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalUsers", userRepository.count());
        stats.put("totalMedicines", medicineRepository.count());
        stats.put("totalOrders", orderRepository.count());
        stats.put("pendingOrders", orderRepository
                .findByStatus(Order.OrderStatus.PENDING).size());
        stats.put("deliveredOrders", orderRepository
                .findByStatus(Order.OrderStatus.DELIVERED).size());
        return stats;
    }

    // Get All Users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Delete User
    public String deleteUser(Integer userId) {
        userRepository.deleteById(userId);
        return "User deleted successfully!";
    }

    // Get All Orders
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // Update Order Status
    public Order updateOrderStatus(Integer orderId, String status) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order == null) return null;
        order.setStatus(Order.OrderStatus.valueOf(status));
        return orderRepository.save(order);
    }

    // Get All Medicines
    public List<Medicine> getAllMedicines() {
        return medicineRepository.findAll();
    }

    // Delete Medicine
    public String deleteMedicine(Integer medicineId) {
        medicineRepository.deleteById(medicineId);
        return "Medicine deleted successfully!";
    }

    // Get Pending Orders
    public List<Order> getPendingOrders() {
        return orderRepository.findByStatus(Order.OrderStatus.PENDING);
    }

    // Get Delivered Orders
    public List<Order> getDeliveredOrders() {
        return orderRepository.findByStatus(Order.OrderStatus.DELIVERED);
    }
}