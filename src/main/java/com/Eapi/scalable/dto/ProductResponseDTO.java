package com.Eapi.scalable.dto;

public class ProductResponseDTO {
    private Long id;
    private String name;
    private String description;
    private double price;
    private int stockQuantity;
    private Long categoryId;
    private String categoryName;

    public ProductResponseDTO(Long id, String name, String description, double price, int stockQuantity, Long categoryId, String categoryName) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    // Getters
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public int getStockQuantity() { return stockQuantity; }
    public Long getCategoryId() { return categoryId; }
    public String getCategoryName() { return categoryName; }
}
