package com.Eapi.scalable.repository;

import com.Eapi.scalable.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepo extends JpaRepository<Cart,Long> {

}
