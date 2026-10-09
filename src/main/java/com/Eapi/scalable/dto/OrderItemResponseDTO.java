//Response DTO
package com.Eapi.scalable.dto;

public class OrderItemResponseDTO {
    private Long id;
    private Long productId;
    private String productName;
    private int quantity;
    private double price;

    public OrderItemResponseDTO(Long id, Long productId, String productName, int quantity, double price){
        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }
    public Long getId() { return id; }
    public Long getProductId() { return productId; }
    public String getProductName() { return productName; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }

}
