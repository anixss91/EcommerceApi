package com.Eapi.scalable.controller;

import com.Eapi.scalable.dto.ProductRequestDTO;
import com.Eapi.scalable.dto.ProductResponseDTO;
import com.Eapi.scalable.model.Product;
import com.Eapi.scalable.repository.ProductRepo;
import com.Eapi.scalable.service.ProductSevice;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProductController {
    @Autowired
    private ProductSevice productSevice;

    @GetMapping("/products")
    public List<ProductResponseDTO> getProducts() {
        return productSevice.getAllProducts().stream()
                .map(productSevice::mapToResponseDTO)
                .collect(Collectors.toList());

    }

    @GetMapping("/products/{id}ظ")
    public ProductResponseDTO getProduct(@PathVariable Long id) {
        Product product = productSevice.getProductById(id);
        return productSevice.mapToResponseDTO(product);
    }

    @GetMapping("/products/category/{categoryId}")
    public List<Product> getProductsByCategory(@PathVariable Long categoryId) {
        return productSevice.getProductByCategory(categoryId);
    }

    @PostMapping("/products")
    @PreAuthorize("hasRole('ADMIN')")
    public ProductResponseDTO addProduct(@Valid @RequestBody ProductRequestDTO request) {
        Product savedProduct = productSevice.createProduct(request);
       return productSevice.mapToResponseDTO(savedProduct);
    }

    @PutMapping("/products/{id}")
    public void updateProduct(@PathVariable Long id, @RequestBody Product product) {
        productSevice.updateProduct(id, product);
    }

    @DeleteMapping("/products/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteProduct(@PathVariable Long id) {
        productSevice.deleteProductById(id);
    }

}

