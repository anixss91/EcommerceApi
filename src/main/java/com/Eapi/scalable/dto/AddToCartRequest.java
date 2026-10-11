package com.Eapi.scalable.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.apache.juli.logging.Log;

public class AddToCartRequest {
    @NotNull(message = "product ID is required")
    private Long productID;

    @NotNull(message = "product quantiy is required")
    @Min(value = 1, message= "at least 1 product")
    private Integer quantity;

    public Long getProductId() { return productID; }
    public void setProductId(Long productId) { this.productID = productID; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

}
