package com.medicine.online_medicine_shop.controller;

import com.medicine.online_medicine_shop.model.Medicine;
import com.medicine.online_medicine_shop.model.Order;
import com.medicine.online_medicine_shop.model.User;
import com.medicine.online_medicine_shop.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired
    private AdminService adminService;

    // Get Dashboard Stats
    @GetMapping("/dashboard")
    public ResponseEntity<Map<String, Object>> getDashboardStats() {
        return ResponseEntity.ok(adminService.getDashboardStats());
    }

    // Get All Users
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(adminService.getAllUsers());
    }

    // Delete User
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<String> deleteUser(
            @PathVariable Integer userId) {
        return ResponseEntity.ok(adminService.deleteUser(userId));
    }

    // Get All Orders
    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(adminService.getAllOrders());
    }

    // Update Order Status
    @PutMapping("/orders/{orderId}/status")
    public ResponseEntity<Order> updateOrderStatus(
            @PathVariable Integer orderId,
            @RequestParam String status) {
        return ResponseEntity.ok(
                adminService.updateOrderStatus(orderId, status));
    }

    // Get All Medicines
    @GetMapping("/medicines")
    public ResponseEntity<List<Medicine>> getAllMedicines() {
        return ResponseEntity.ok(adminService.getAllMedicines());
    }

    // Delete Medicine
    @DeleteMapping("/medicines/{medicineId}")
    public ResponseEntity<String> deleteMedicine(
            @PathVariable Integer medicineId) {
        return ResponseEntity.ok(adminService.deleteMedicine(medicineId));
    }

    // Get Pending Orders
    @GetMapping("/orders/pending")
    public ResponseEntity<List<Order>> getPendingOrders() {
        return ResponseEntity.ok(adminService.getPendingOrders());
    }

    // Get Delivered Orders
    @GetMapping("/orders/delivered")
    public ResponseEntity<List<Order>> getDeliveredOrders() {
        return ResponseEntity.ok(adminService.getDeliveredOrders());
    }
}