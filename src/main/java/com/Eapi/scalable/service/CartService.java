package com.Eapi.scalable.service;

import com.Eapi.scalable.dto.CartItemResponseDTO;
import com.Eapi.scalable.dto.CartResponseDTO;
import com.Eapi.scalable.exception.ResourceNotFountException;
import com.Eapi.scalable.model.Cart;
import com.Eapi.scalable.model.CartItem;
import com.Eapi.scalable.model.Product;
import com.Eapi.scalable.model.User;
import com.Eapi.scalable.repository.CartItemRepo;
import com.Eapi.scalable.repository.CartRepo;
import com.Eapi.scalable.repository.ProductRepo;
import com.Eapi.scalable.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartService {

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private CartItemRepo cartItemRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ProductRepo productRepo;

    public Cart getCartById(Long id) {
        return cartRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFountException("Cart not found"));
    }

    public Cart addItemToCart(String email, Long productId, int quantity) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFountException("User not found"));

        Cart cart = cartRepo.findByUser_Id(user.getId()).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setUser(user);
            return cartRepo.save(newCart);
        });

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ResourceNotFountException("Product not found!"));

        Optional<CartItem> existingItem = cartItemRepo.findByCart_IdAndProduct_Id(cart.getId(), productId);
        if (existingItem.isPresent()) {
            CartItem cartItem = existingItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartItemRepo.save(cartItem);
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItemRepo.save(cartItem);
        }

        // Reload cart to ensure items collection is fresh
        return getCartByUserEmail(email);
    }

    public Cart getCartByUserEmail(String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFountException("User not found"));
        return cartRepo.findByUser_Id(user.getId())
                .orElseThrow(() -> new ResourceNotFountException("Cart not found!"));
    }

    public Cart updateCartItemQuantity(String email, Long cartItemId, int quantity) {
        CartItem cartItem = cartItemRepo.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFountException("Cart item not found"));

        cartItem.setQuantity(quantity);
        cartItemRepo.save(cartItem);

        return getCartByUserEmail(email);
    }

    public Cart removeItemFromCart(String email, Long cartItemId) {
        cartItemRepo.deleteById(cartItemId);
        return getCartByUserEmail(email);
    }

    public void clearCart(String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFountException("User not found"));
        cartRepo.findByUser_Id(user.getId()).ifPresent(cart -> {
            cartItemRepo.deleteAllByCart_Id(cart.getId());
        });
    }

    public CartResponseDTO mapToResponseDto(Cart cart) {
        List<CartItemResponseDTO> itemDTOs = cart.getItems().stream()
                .map(item -> new CartItemResponseDTO(
                        item.getId(),
                        item.getProduct().getId(),
                        item.getProduct().getName(),
                        item.getProduct().getPrice(),
                        item.getQuantity()
                ))
                .collect(Collectors.toList());

        double grandTotal = itemDTOs.stream()
                .mapToDouble(CartItemResponseDTO::getSubTotal)
                .sum();

        return new CartResponseDTO(
                cart.getId(),
                cart.getUser().getEmail(),
                grandTotal,
                itemDTOs
        );
    }
}