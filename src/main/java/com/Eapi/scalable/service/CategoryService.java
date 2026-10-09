package com.Eapi.scalable.service;

import com.Eapi.scalable.dto.CategoryResponseDto;
import com.Eapi.scalable.exception.ResourceNotFountException;
import com.Eapi.scalable.model.Category;
import com.Eapi.scalable.model.Product;
import com.Eapi.scalable.repository.CategoryRepo;
import com.Eapi.scalable.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepo categoryRepo;

    public CategoryResponseDto mapToResponseDTO(Category category){
        return new CategoryResponseDto(category.getId(), category.getName());
    }
    public Category saveCategory(Category category) {
        return categoryRepo.save(category);
    }

    public List<Category> getAllCategories() {
        return categoryRepo.findAll();
    }
    public Category getCategoryById(Long id) {
        return categoryRepo.findById(id).orElseThrow(() -> new ResourceNotFountException("Category not found!"));
    }
    public Category addCategory(Category category) {
      return  categoryRepo.save(category);
    }
    public void deleteCategoryById(Long id ) {
        categoryRepo.deleteById(id);
    }


}
