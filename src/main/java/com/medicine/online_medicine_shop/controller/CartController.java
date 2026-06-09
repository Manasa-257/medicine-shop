package com.medicine.online_medicine_shop.controller;

import com.medicine.online_medicine_shop.dto.CartRequest;
import com.medicine.online_medicine_shop.model.Cart;
import com.medicine.online_medicine_shop.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "*")
public class CartController {

    @Autowired
    private CartService cartService;

    // Add to Cart
    @PostMapping("/add")
    public ResponseEntity<Cart> addToCart(
            @RequestBody CartRequest request) {
        return ResponseEntity.ok(cartService.addToCart(request));
    }

    // Get Cart by User
    @GetMapping("/{userId}")
    public ResponseEntity<List<Cart>> getCartByUser(
            @PathVariable Integer userId) {
        return ResponseEntity.ok(cartService.getCartByUser(userId));
    }

    // Update Cart Quantity
    @PutMapping("/{cartId}")
    public ResponseEntity<Cart> updateCartQuantity(
            @PathVariable Integer cartId,
            @RequestParam Integer quantity) {
        return ResponseEntity.ok(
                cartService.updateCartQuantity(cartId, quantity));
    }

    // Remove from Cart
    @DeleteMapping("/{cartId}")
    public ResponseEntity<String> removeFromCart(
            @PathVariable Integer cartId) {
        return ResponseEntity.ok(cartService.removeFromCart(cartId));
    }

    // Clear Cart
    @DeleteMapping("/clear/{userId}")
    public ResponseEntity<String> clearCart(
            @PathVariable Integer userId) {
        return ResponseEntity.ok(cartService.clearCart(userId));
    }
}