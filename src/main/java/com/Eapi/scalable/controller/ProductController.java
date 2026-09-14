package com.Eapi.scalable.controller;

import com.Eapi.scalable.model.Product;
import com.Eapi.scalable.repository.ProductRepo;
import com.Eapi.scalable.service.ProductSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {
    @Autowired
    private ProductSevice productSevice;

    @GetMapping("/products")
    public List<Product> getProducts() {
        return productSevice.getAllProducts();
    }

    @GetMapping("/products/{id}ظ")
    public Product getProduct(@PathVariable Long id) {
        return productSevice.getProductById(id);
    }

    @GetMapping("/products/category/{categoryId}")
    public List<Product> getProductsByCategory(@PathVariable Long categoryId) {
        return productSevice.getProductByCategory(categoryId);
    }

    @PostMapping("/products")
    public Product addProduct(@RequestBody Product product) {
       return productSevice.createProduct(product);
    }

    @PutMapping("/products/{id}")
    public void updateProduct(@PathVariable Long id, @RequestBody Product product) {
        productSevice.updateProduct(id, product);
    }

    @DeleteMapping("/products/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productSevice.deleteProduct(id);
    }

}

