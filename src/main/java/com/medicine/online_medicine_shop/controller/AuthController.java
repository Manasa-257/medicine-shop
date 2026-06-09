package com.medicine.online_medicine_shop.controller;

import com.medicine.online_medicine_shop.dto.LoginRequest;
import com.medicine.online_medicine_shop.dto.RegisterRequest;
import com.medicine.online_medicine_shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserService userService;

    // Register API
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(
            @RequestBody RegisterRequest request) {
        Map<String, String> response = userService.register(request);
        return ResponseEntity.ok(response);
    }

    // Login API
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(
            @RequestBody LoginRequest request) {
        Map<String, String> response = userService.login(request);
        return ResponseEntity.ok(response);
    }
}