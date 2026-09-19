package com.Eapi.scalable.controller;

import com.Eapi.scalable.model.Order;
import com.Eapi.scalable.repository.UserRepo;
import com.Eapi.scalable.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

        private String getCurrentUserEmail(){
            return SecurityContextHolder.getContext().getAuthentication().getName();
        }

    @PostMapping("/orders/checkout")
    public Order checkout(){
     return   orderService.checkout(getCurrentUserEmail());
    }

    @GetMapping("/orders/my-orders")
    public List<Order> getMyOrders() {

        return orderService.getOrdersByUserEmail(getCurrentUserEmail());
    }

    @GetMapping("/orders/{id}")
    public Order getOrderById(@PathVariable Long id) {
            Order order = orderService.getOrderById(id);
            String currentUserEmail = getCurrentUserEmail();
            if(!order.getUser().getEmail().equals(currentUserEmail)){
                throw new RuntimeException("you are not allowed to view this order");
            }
        return order;
    }


    @PatchMapping("/orders/{id}/status")
    @PreAuthorize("hasRole('ADMIN")
    public void updateOrder(@PathVariable Long id, @RequestBody Order order) {
        orderService.updateOrderStatus(id, order.getStatus());
    }

    @DeleteMapping("/orders/{id}")
    @PreAuthorize("hasRole('ADMIN")
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteOrderById(id);
    }
}
