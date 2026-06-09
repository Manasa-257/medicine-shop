package com.medicine.online_medicine_shop.dto;

import lombok.Data;

@Data
public class CartRequest {
    private Integer userId;
    private Integer medicineId;
    private Integer quantity;
}