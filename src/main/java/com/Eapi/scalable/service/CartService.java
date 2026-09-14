package com.Eapi.scalable.service;

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

import java.util.Optional;

@Service
public class CartService {
    @Autowired
    private CartRepo cartRepo;

    @Autowired
    CartItemRepo cartItemRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    ProductRepo productRepo;
public Cart getCartById(Long id) {
   return cartRepo.findById(id).orElseThrow(() -> new RuntimeException("Cart not found!"));
}

public Cart addItemToCart(Long userId , Long productId, int quantity) {
    User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("user not found"));
    //orElseGet(() -> ...) — only executes the code inside when the Optional IS empty. It's lazy — it only runs when needed.
    Cart cart = cartRepo.findByUser_Id(userId).orElseGet(()-> {
        Cart newCart = new Cart();
        newCart.setUser(user);
        return cartRepo.save(newCart);
    });

    Product product =productRepo.findById(productId).orElseThrow(() -> new RuntimeException("product not found!"));

    Optional <CartItem> existingItem = cartItemRepo.findByCart_IdAndProduct_Id(cart.getId() , productId);
    if(existingItem.isPresent()) {
        //update quantity
        CartItem cartItem = existingItem.get();
        cartItem.setQuantity(cartItem.getQuantity() + quantity);
        cartItemRepo.save(cartItem);

    }else {
        //create new cart item
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        cartItemRepo.save(cartItem);
    }
    return cart;
}

}
