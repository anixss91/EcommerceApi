    package com.Eapi.scalable.service;

    import com.Eapi.scalable.model.Product;
    import com.Eapi.scalable.repository.ProductRepo;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;

    import java.util.List;

    @Service
    public class ProductSevice {
        @Autowired
        private ProductRepo productRepo;

        public List<Product> getAllProducts() {
            return productRepo.findAll();
        }

        public Product getProductById(Long id) {
            return productRepo.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));

        }

        public List<Product> getProductByCategory(Long CategoryId) {
           return productRepo.findByCategory_Id(CategoryId);
        }

        public Product createProduct(Product product) {
            return productRepo.save(product);
        }

        public void updateProduct(Long id , Product product) {
            productRepo.findById(id).ifPresent(product1 -> {
                product1.setCategory(product.getCategory());
                product1.setDescription(product.getDescription());
                product1.setPrice(product.getPrice());
                product1.setName(product.getName());
                product1.setStockQuantity(product.getStockQuantity());
                productRepo.save(product1);
            });
        }

        public void deleteProduct(Long id) {
            productRepo.deleteById(id);
        }

    }
