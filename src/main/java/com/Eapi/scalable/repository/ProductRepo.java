package com.Eapi.scalable.repository;

import com.Eapi.scalable.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product,Long> {
    List<Product> findByCategory_Id(Long categoryId);
}
