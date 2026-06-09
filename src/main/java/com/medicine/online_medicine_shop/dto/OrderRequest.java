package com.medicine.online_medicine_shop.dto;

import lombok.Data;

@Data
public class OrderRequest {
    private Integer userId;
    private String deliveryAddress;
    private String paymentMethod;
}