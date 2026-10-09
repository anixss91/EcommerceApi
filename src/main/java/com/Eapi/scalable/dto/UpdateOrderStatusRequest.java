
//Request DTO
package com.Eapi.scalable.dto;


import jakarta.validation.constraints.NotBlank;

public class UpdateOrderStatusRequest {
    @NotBlank(message = "Status is required and cannot be blanck")
    private String status;

    public String getStatus(){return status;}
    public void setStatus(){this.status = status;}
}
