package com.Eapi.scalable.repository;

import com.Eapi.scalable.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category,Long> {
}
