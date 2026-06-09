package com.medicine.online_medicine_shop.repository;

import com.medicine.online_medicine_shop.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByUserUserId(Integer userId);

    List<Order> findByStatus(Order.OrderStatus status);
}