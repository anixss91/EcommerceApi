package com.Eapi.scalable.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ProductRequestDTO {
    @NotBlank(message = "Product neme is required")
    private String name;

    private String description;
    @NotNull(message = "price is rewuired")
    @Positive(message = "price must be greater than zero")
    private double price ;

    @NotNull(message = " Stock quantity is required")
    @Min(value = 0 , message = "Stock quantity cannot be negative")
    private Integer stockQuantity;

    @NotNull(message = "Category Id is required")
    private Long categoryId;


    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public Integer getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(Integer stockQuantity) { this.stockQuantity = stockQuantity; }

    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }


}
