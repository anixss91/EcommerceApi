package com.Eapi.scalable.service;

import com.Eapi.scalable.model.Order;
import com.Eapi.scalable.repository.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepo orderRepo;

    public List<Order> getAllOrders() {
        return orderRepo.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepo.findById(id).orElse(null);
    }

    public Order addOrder(Order order) {
        return orderRepo.save(order);
    }

    public void updateOrderStatus(Long id, String status) {
        orderRepo.findById(id).ifPresent(order -> {
            order.setStatus(status);
            orderRepo.save(order);
        });
    }

    public void deleteOrderById(Long id) {
        orderRepo.deleteById(id);
    }

}


