package com.Eapi.scalable.controller;

import com.Eapi.scalable.dto.OrderResponseDTO;
import com.Eapi.scalable.dto.UpdateOrderStatusRequest;
import com.Eapi.scalable.exception.BadRequestException;
import com.Eapi.scalable.model.Order;
import com.Eapi.scalable.service.OrderService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

        private String getCurrentUserEmail(){
            return SecurityContextHolder.getContext().getAuthentication().getName();
        }

    @PostMapping("/orders/checkout")
    @Transactional//guarantees that if any step fails, all database changes roll back automatically.
    public OrderResponseDTO checkout(){
            Order order = orderService.checkout(getCurrentUserEmail());
     return   orderService.mapToOrderResponseDto(order);
    }

    @GetMapping("/orders/my-orders")
    public List<OrderResponseDTO> getMyOrders() {

        return orderService.getOrdersByUserEmail(getCurrentUserEmail())
                .stream()
                .map(orderService :: mapToOrderResponseDto)
                .collect(Collectors.toUnmodifiableList());
    }

    @GetMapping("/orders/{id}")
    public OrderResponseDTO getOrderById(@PathVariable Long id) {
            Order order = orderService.getOrderById(id);
            String currentUserEmail = getCurrentUserEmail();
            if(!order.getUser().getEmail().equals(currentUserEmail)){
                throw new BadRequestException("you are not allowed to view this order");
            }
        return orderService.mapToOrderResponseDto(order);
    }


    @PatchMapping("/orders/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public void updateOrderStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateOrderStatusRequest request) {

        orderService.updateOrderStatus(id, request.getStatus());
    }

    @DeleteMapping("/orders/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteOrderById(id);
    }
}
