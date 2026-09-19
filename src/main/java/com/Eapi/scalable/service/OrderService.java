package com.Eapi.scalable.service;

import com.Eapi.scalable.model.*;
import com.Eapi.scalable.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private CartItemRepo cartItemRepo;

    @Autowired
   private UserRepo userRepo;

    @Autowired
    private ProductRepo productRepo;

    public Order checkout(String email) {
        //1-find the user
        User user = userRepo.findByEmail(email).orElseThrow(()->new RuntimeException("user not found"));

        //2-find their cart
        Cart cart = cartRepo.findByUser_Id(user.getId()).orElseThrow(()->new RuntimeException("cart not found"));

        //3-get all itemsof the cart
        List <CartItem> cartItems = cartItemRepo.findByCart_Id(cart.getId());

        //4.cant checkout an empty cart
        if(cartItems.isEmpty()){
            throw new RuntimeException("cart is empty");
        }

        //5-create new order
        Order order = new Order();
        order.setUser(user);
        order.setStatus("pending");
        order.setCreatedAt(LocalDateTime.now());

        //6-build OrderItems and calculate total
            List<OrderItem> orderItems = new ArrayList<>();
            double totalPrice = 0;
            for (CartItem cartItem : cartItems) {
                Product product = cartItem.getProduct();

                //decrease stok
                product.setStockQuantity(product.getStockQuantity() - cartItem.getQuantity());
                productRepo.save(product);

                //7-create order item
                OrderItem orderItem = new OrderItem();
                orderItem.setProduct(product);
                orderItem.setQuantity(cartItem.getQuantity());
                orderItem.setPrice(product.getPrice());
                orderItem.setOrder(order);

                orderItems.add(orderItem);
                totalPrice += product.getPrice() * cartItem.getQuantity();

            }

       order.setOrderItems(orderItems);
        order.setTotalPrice(totalPrice);

        Order savedOrder = orderRepo.save(order);

        //cleaar the cart
        cartItemRepo.deleteAllByCart_Id(cart.getId());

        return savedOrder;
    }


    public List<Order> getAllOrders() {
        return orderRepo.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepo.findById(id).orElseThrow(()->new RuntimeException("order not found"));
    }

    public List <Order>getOrdersByUserEmail(String email){
        User user = userRepo.findByEmail(email).orElseThrow(()->new RuntimeException("user not found"));
        return orderRepo.findByUser_Id(user.getId());
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


