package com.Eapi.scalable.dto;

import java.time.LocalDateTime;
import java.util.List;

public class OrderResponseDTO {
    private long id;
    private String userEmail;
    private String status;
    private double totalPrice;
    private LocalDateTime createdAt;
    private List<OrderItemResponseDTO> items;

    public OrderResponseDTO(Long id, String userEmail, String status, double totalPrice, LocalDateTime createdAt, List<OrderItemResponseDTO> items) {
        this.id = id;
        this.userEmail = userEmail;
        this.status = status;
        this.totalPrice = totalPrice;
        this.createdAt = createdAt;
        this.items = items;
    }

    public Long getId() { return id; }
    public String getUserEmail() { return userEmail; }
    public String getStatus() { return status; }
    public double getTotalPrice() { return totalPrice; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public List<OrderItemResponseDTO> getItems() { return items; }
}
