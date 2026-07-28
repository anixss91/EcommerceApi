package com.Eapi.scalable.repository;

import com.Eapi.scalable.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product,Long> {
}
