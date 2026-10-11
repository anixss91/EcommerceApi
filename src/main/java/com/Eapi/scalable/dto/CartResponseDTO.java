package com.Eapi.scalable.dto;

import java.util.List;

public class CartResponseDTO {
    private Long id;
    private String userEmail;
    private double totalPrice;
    private List<CartItemResponseDTO> items;

    public CartResponseDTO(Long id, String userEmail, double totalPrice, List<CartItemResponseDTO> items) {
        this.id = id;
        this.userEmail = userEmail;
        this.totalPrice = totalPrice;
        this.items = items;
    }

    public Long getId() { return id; }
    public String getUserEmail() { return userEmail; }
    public double getTotalPrice() { return totalPrice; }
    public List<CartItemResponseDTO> getItems() { return items; }
}
