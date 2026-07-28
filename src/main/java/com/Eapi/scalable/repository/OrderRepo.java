package com.Eapi.scalable.repository;

import com.Eapi.scalable.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order,Long> {
}
