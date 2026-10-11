package com.Eapi.scalable.dto;

public class CartItemResponseDTO {

    private Long id;
    private Long productId;
    private String productName;
    private double unitPrice;
    private int quantity;
    private double subTotal;

    public CartItemResponseDTO(Long id, Long productId, String productName, double unitPrice, int quantity) {
        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.subTotal = unitPrice * quantity;
    }

    public Long getId() { return id; }
    public Long getProductId() { return productId; }
    public String getProductName() { return productName; }
    public double getUnitPrice() { return unitPrice; }
    public int getQuantity() { return quantity; }
    public double getSubTotal() { return subTotal; }
}