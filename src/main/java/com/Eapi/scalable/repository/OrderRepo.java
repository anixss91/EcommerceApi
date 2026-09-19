package com.Eapi.scalable.repository;

import com.Eapi.scalable.model.Cart;
import com.Eapi.scalable.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepo extends JpaRepository<Order,Long> {
    List<Order> findByUser_Id(Long userId);
}
