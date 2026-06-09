package com.medicine.online_medicine_shop.service;

import com.medicine.online_medicine_shop.dto.CartRequest;
import com.medicine.online_medicine_shop.model.Cart;
import com.medicine.online_medicine_shop.model.Medicine;
import com.medicine.online_medicine_shop.model.User;
import com.medicine.online_medicine_shop.repository.CartRepository;
import com.medicine.online_medicine_shop.repository.MedicineRepository;
import com.medicine.online_medicine_shop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MedicineRepository medicineRepository;

    // Add to Cart
    public Cart addToCart(CartRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElse(null);
        Medicine medicine = medicineRepository.findById(request.getMedicineId())
                .orElse(null);

        if (user == null || medicine == null) return null;

        // Check if medicine already in cart
        Optional<Cart> existingCart = cartRepository
                .findByUserUserIdAndMedicineMedicineId(
                        request.getUserId(), request.getMedicineId());

        if (existingCart.isPresent()) {
            // Update quantity
            Cart cart = existingCart.get();
            cart.setQuantity(cart.getQuantity() + request.getQuantity());
            return cartRepository.save(cart);
        }

        // Add new cart item
        Cart cart = new Cart();
        cart.setUser(user);
        cart.setMedicine(medicine);
        cart.setQuantity(request.getQuantity());
        return cartRepository.save(cart);
    }

    // Get Cart by User
    public List<Cart> getCartByUser(Integer userId) {
        return cartRepository.findByUserUserId(userId);
    }

    // Update Cart Quantity
    public Cart updateCartQuantity(Integer cartId, Integer quantity) {
        Cart cart = cartRepository.findById(cartId).orElse(null);
        if (cart == null) return null;
        cart.setQuantity(quantity);
        return cartRepository.save(cart);
    }

    // Remove from Cart
    public String removeFromCart(Integer cartId) {
        cartRepository.deleteById(cartId);
        return "Item removed from cart!";
    }

    // Clear Cart
    public String clearCart(Integer userId) {
        cartRepository.deleteByUserUserId(userId);
        return "Cart cleared successfully!";
    }
}