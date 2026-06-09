package com.medicine.online_medicine_shop.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}