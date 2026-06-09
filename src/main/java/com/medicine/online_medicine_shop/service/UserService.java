package com.medicine.online_medicine_shop.service;

import com.medicine.online_medicine_shop.dto.LoginRequest;
import com.medicine.online_medicine_shop.dto.RegisterRequest;
import com.medicine.online_medicine_shop.model.User;
import com.medicine.online_medicine_shop.repository.UserRepository;
import com.medicine.online_medicine_shop.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    // Register User
    public Map<String, String> register(RegisterRequest request) {
        Map<String, String> response = new HashMap<>();

        if (userRepository.existsByEmail(request.getEmail())) {
            response.put("message", "Email already exists!");
            return response;
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhone(request.getPhone());
        user.setAddress(request.getAddress());
        user.setRole(User.Role.USER);

        userRepository.save(user);

        response.put("message", "User registered successfully!");
        return response;
    }

    // Login User
    public Map<String, String> login(LoginRequest request) {
        Map<String, String> response = new HashMap<>();

        User user = userRepository.findByEmail(request.getEmail())
                .orElse(null);

        if (user == null) {
            response.put("message", "User not found!");
            return response;
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            response.put("message", "Invalid password!");
            return response;
        }

        String token = jwtUtil.generateToken(user.getEmail());

        response.put("message", "Login successful!");
        response.put("token", token);
        response.put("name", user.getName());
        response.put("role", user.getRole().toString());
        response.put("userId", user.getUserId().toString());

        return response;
    }
}