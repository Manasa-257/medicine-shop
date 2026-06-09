package com.medicine.online_medicine_shop.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "medicines")
public class Medicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer medicineId;

    @Column(nullable = false)
    private String name;

    private String brand;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(nullable = false)
    private Double price;

    private Integer stock;

    private String description;

    private Boolean prescriptionRequired = false;

    private String imageUrl;
}