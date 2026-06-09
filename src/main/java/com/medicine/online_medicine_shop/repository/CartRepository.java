package com.medicine.online_medicine_shop.repository;

import com.medicine.online_medicine_shop.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

    List<Cart> findByUserUserId(Integer userId);

    Optional<Cart> findByUserUserIdAndMedicineMedicineId(
            Integer userId, Integer medicineId);

    @Transactional
    void deleteByUserUserId(Integer userId);
}