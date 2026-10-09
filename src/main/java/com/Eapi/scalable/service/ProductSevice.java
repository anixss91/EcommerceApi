    package com.Eapi.scalable.service;

    import com.Eapi.scalable.dto.ProductRequestDTO;
    import com.Eapi.scalable.dto.ProductResponseDTO;
    import com.Eapi.scalable.exception.ResourceNotFountException;
    import com.Eapi.scalable.model.Category;
    import com.Eapi.scalable.model.Product;
    import com.Eapi.scalable.repository.CategoryRepo;
    import com.Eapi.scalable.repository.ProductRepo;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;

    import java.util.List;

    @Service
    public class ProductSevice {
        @Autowired
        private ProductRepo productRepo;
        @Autowired
        private CategoryRepo categoryRepo;

        public ProductResponseDTO mapToResponseDTO(Product product){
            return new ProductResponseDTO(
                    product.getId(),
                    product.getName(),
                    product.getDescription(),
                    product.getPrice(),
                    product.getStockQuantity(),
                    product.getCategory() != null ? product.getCategory().getId() : null,
                    product.getCategory() != null ? product.getCategory().getName() : null

            );
        }

        public List<Product> getAllProducts() {
            return productRepo.findAll();
        }

        public Product getProductById(Long id) {
            return productRepo.findById(id).orElseThrow(() -> new ResourceNotFountException("Product not found"));

        }

        public List<Product> getProductByCategory(Long CategoryId) {
           return productRepo.findByCategory_Id(CategoryId);
        }

        public Product createProduct(ProductRequestDTO dto) {
            Category category = categoryRepo.findById(dto.getCategoryId())
                    .orElseThrow(()-> new ResourceNotFountException("category Not found"));

            Product product = new Product();
            product.setName(dto.getName());
            product.setDescription(dto.getDescription());
            product.setPrice(dto.getPrice());
            product.setStockQuantity(dto.getStockQuantity());
            product.setCategory(category);

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

        public void deleteProductById(Long id) {
            if (!productRepo.existsById(id)) {
                throw new ResourceNotFountException("Product not found with id: " + id);
            }
            productRepo.deleteById(id);
        }

    }
