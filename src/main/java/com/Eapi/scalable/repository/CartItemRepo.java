package com.Eapi.scalable.repository;

import com.Eapi.scalable.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepo extends JpaRepository<CartItem,Long> {
    List<CartItem> findByProduct_Id(Long ProductId);
    Optional<CartItem> findByCart_IdAndProduct_Id(Long cartId, Long productId);
    void deleteAllByCart_Id(Long cartId);
}
