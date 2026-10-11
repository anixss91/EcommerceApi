package com.Eapi.scalable.controller;

import com.Eapi.scalable.dto.AddToCartRequest;
import com.Eapi.scalable.dto.CartResponseDTO;
import com.Eapi.scalable.model.Cart;
import com.Eapi.scalable.service.CartService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    private String getCurrentUserEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @GetMapping
    public CartResponseDTO getMyCart() {
        Cart cart = cartService.getCartByUserEmail(getCurrentUserEmail());
        return cartService.mapToResponseDto(cart);
    }

    @PostMapping("/add")
    public CartResponseDTO addItemToCart(@Valid @RequestBody AddToCartRequest request) {
        Cart cart = cartService.addItemToCart(
                getCurrentUserEmail(),
                request.getProductId(),
                request.getQuantity()
        );
        return cartService.mapToResponseDto(cart);
    }

    @PutMapping("/items/{cartItemId}")
    public CartResponseDTO updateCartItemQuantity(
            @PathVariable Long cartItemId,
            @RequestParam int quantity) {
        Cart cart = cartService.updateCartItemQuantity(getCurrentUserEmail(), cartItemId, quantity);
        return cartService.mapToResponseDto(cart);
    }

    @DeleteMapping("/items/{cartItemId}")
    public CartResponseDTO deleteCartItem(@PathVariable Long cartItemId) {
        Cart cart = cartService.removeItemFromCart(getCurrentUserEmail(), cartItemId);
        return cartService.mapToResponseDto(cart);
    }

    @DeleteMapping("/clear")
    public void clearCart() {
        cartService.clearCart(getCurrentUserEmail());
    }
}