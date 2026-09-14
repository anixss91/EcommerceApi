package com.Eapi.scalable.repository;

import com.Eapi.scalable.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepo extends JpaRepository<Cart,Long> {
    Optional<Cart> findByUser_Id(Long userId);
}
