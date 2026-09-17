package com.Eapi.scalable.controller;

import com.Eapi.scalable.model.Cart;
import com.Eapi.scalable.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    private String getCurrentUserEmail(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @GetMapping("/{id}")
    public Cart getCartById(@PathVariable Long id){
       return cartService.getCartById(id);
    }

    @PostMapping("/add/{productId}")
    public Cart addItemToCart( @PathVariable Long productId ,@RequestParam int  quantity){
        return cartService.addItemToCart(getCurrentUserEmail() , productId , quantity);
    }

    @GetMapping("/my-cart")
    public Cart getCartByUserId(){
      return   cartService.getCartByUserEmail(getCurrentUserEmail());
    }

    @PutMapping("/{cartItemId}")
    public void updateCaryItemQuantity(@PathVariable Long cartItemId , int quantity){
        cartService.updateCartIemQuantity(cartItemId, quantity);
    }

    @DeleteMapping("/cart/{cartItemId}")
    public void deleteCartItem(@PathVariable Long cartItemId){
        cartService.removeItemFromCart(cartItemId);
    }

    @DeleteMapping("/clear")
    public void deleteCartByUserId(){
        cartService.clearCart(getCurrentUserEmail());
    }


}
