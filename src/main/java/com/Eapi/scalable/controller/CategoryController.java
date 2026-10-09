package com.Eapi.scalable.controller;

import com.Eapi.scalable.dto.CategoryRequestDto;
import com.Eapi.scalable.dto.CategoryResponseDto;
import com.Eapi.scalable.model.Category;
import com.Eapi.scalable.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categories")
    public List<CategoryResponseDto> getCategories() {
       return categoryService.getAllCategories()
               .stream()
               .map(categoryService :: mapToResponseDTO )
               .collect(Collectors.toList());

    }



    @GetMapping("/categories/{id}")
    public CategoryResponseDto getCategoryById(@PathVariable Long id) {
        Category category = categoryService.getCategoryById(id);
        return categoryService.mapToResponseDTO(category);
    }

    @PostMapping("/categories")
    @PreAuthorize("hasRole('Admin')")
    public CategoryResponseDto addCategory(@Valid  @RequestBody CategoryRequestDto request) {
        Category category = new Category();
        category.setName(request.getName());
        Category savedCategory = categoryService.saveCategory(category);
        return categoryService.mapToResponseDTO(savedCategory);
    }

    @DeleteMapping("/categories/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategoryById(id);
    }
}
