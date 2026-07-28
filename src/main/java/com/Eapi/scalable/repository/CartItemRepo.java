package com.Eapi.scalable.repository;

import com.Eapi.scalable.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepo extends JpaRepository<CartItem,Long> {
}
