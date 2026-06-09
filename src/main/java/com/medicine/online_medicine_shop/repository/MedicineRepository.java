package com.medicine.online_medicine_shop.repository;

import com.medicine.online_medicine_shop.model.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Integer> {

    List<Medicine> findByNameContainingIgnoreCase(String name);

    List<Medicine> findByCategoryCategoryId(Integer categoryId);

    List<Medicine> findByPrescriptionRequired(Boolean prescriptionRequired);
}