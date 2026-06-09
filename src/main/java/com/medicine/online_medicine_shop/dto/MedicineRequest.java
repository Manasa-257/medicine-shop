package com.medicine.online_medicine_shop.dto;

import lombok.Data;

@Data
public class MedicineRequest {
    private String name;
    private String brand;
    private Integer categoryId;
    private Double price;
    private Integer stock;
    private String description;
    private Boolean prescriptionRequired;
    private String imageUrl;
}