package com.medicine.online_medicine_shop.service;

import com.medicine.online_medicine_shop.dto.MedicineRequest;
import com.medicine.online_medicine_shop.model.Category;
import com.medicine.online_medicine_shop.model.Medicine;
import com.medicine.online_medicine_shop.repository.CategoryRepository;
import com.medicine.online_medicine_shop.repository.MedicineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicineService {

    @Autowired
    private MedicineRepository medicineRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    // Get All Medicines
    public List<Medicine> getAllMedicines() {
        return medicineRepository.findAll();
    }

    // Get Medicine by ID
    public Medicine getMedicineById(Integer id) {
        return medicineRepository.findById(id).orElse(null);
    }

    // Search Medicine by Name
    public List<Medicine> searchMedicines(String name) {
        return medicineRepository.findByNameContainingIgnoreCase(name);
    }

    // Get Medicines by Category
    public List<Medicine> getMedicinesByCategory(Integer categoryId) {
        return medicineRepository.findByCategoryCategoryId(categoryId);
    }

    // Add Medicine
    public Medicine addMedicine(MedicineRequest request) {
        Medicine medicine = new Medicine();
        medicine.setName(request.getName());
        medicine.setBrand(request.getBrand());
        medicine.setPrice(request.getPrice());
        medicine.setStock(request.getStock());
        medicine.setDescription(request.getDescription());
        medicine.setPrescriptionRequired(request.getPrescriptionRequired());
        medicine.setImageUrl(request.getImageUrl());

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElse(null);
        medicine.setCategory(category);

        return medicineRepository.save(medicine);
    }

    // Update Medicine
    public Medicine updateMedicine(Integer id, MedicineRequest request) {
        Medicine medicine = medicineRepository.findById(id).orElse(null);
        if (medicine == null) return null;

        medicine.setName(request.getName());
        medicine.setBrand(request.getBrand());
        medicine.setPrice(request.getPrice());
        medicine.setStock(request.getStock());
        medicine.setDescription(request.getDescription());
        medicine.setPrescriptionRequired(request.getPrescriptionRequired());
        medicine.setImageUrl(request.getImageUrl());

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElse(null);
        medicine.setCategory(category);

        return medicineRepository.save(medicine);
    }

    // Delete Medicine
    public String deleteMedicine(Integer id) {
        medicineRepository.deleteById(id);
        return "Medicine deleted successfully!";
    }

    // Get All Categories
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    // Add Category
    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }
}