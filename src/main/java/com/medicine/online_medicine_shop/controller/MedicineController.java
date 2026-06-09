package com.medicine.online_medicine_shop.controller;

import com.medicine.online_medicine_shop.dto.MedicineRequest;
import com.medicine.online_medicine_shop.model.Category;
import com.medicine.online_medicine_shop.model.Medicine;
import com.medicine.online_medicine_shop.service.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicines")
@CrossOrigin(origins = "*")
public class MedicineController {

    @Autowired
    private MedicineService medicineService;

    // Get All Medicines
    @GetMapping
    public ResponseEntity<List<Medicine>> getAllMedicines() {
        return ResponseEntity.ok(medicineService.getAllMedicines());
    }

    // Get Medicine by ID
    @GetMapping("/{id}")
    public ResponseEntity<Medicine> getMedicineById(@PathVariable Integer id) {
        return ResponseEntity.ok(medicineService.getMedicineById(id));
    }

    // Search Medicine by Name
    @GetMapping("/search")
    public ResponseEntity<List<Medicine>> searchMedicines(
            @RequestParam String name) {
        return ResponseEntity.ok(medicineService.searchMedicines(name));
    }

    // Get Medicines by Category
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Medicine>> getMedicinesByCategory(
            @PathVariable Integer categoryId) {
        return ResponseEntity.ok(medicineService.getMedicinesByCategory(categoryId));
    }

    // Add Medicine
    @PostMapping
    public ResponseEntity<Medicine> addMedicine(
            @RequestBody MedicineRequest request) {
        return ResponseEntity.ok(medicineService.addMedicine(request));
    }

    // Update Medicine
    @PutMapping("/{id}")
    public ResponseEntity<Medicine> updateMedicine(
            @PathVariable Integer id,
            @RequestBody MedicineRequest request) {
        return ResponseEntity.ok(medicineService.updateMedicine(id, request));
    }

    // Delete Medicine
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMedicine(@PathVariable Integer id) {
        return ResponseEntity.ok(medicineService.deleteMedicine(id));
    }

    // Get All Categories
    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(medicineService.getAllCategories());
    }

    // Add Category
    @PostMapping("/categories")
    public ResponseEntity<Category> addCategory(
            @RequestBody Category category) {
        return ResponseEntity.ok(medicineService.addCategory(category));
    }
}