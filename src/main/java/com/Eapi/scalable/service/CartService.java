package com.Eapi.scalable.service;

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
   return cartRepo.findById(id).orElseThrow(() -> new ResourceNotFountException("Cart not found"));
}

public Cart addItemToCart(String email, Long productId, int quantity) {
    User user = userRepo.findByEmail(email).orElseThrow(() -> new ResourceNotFountException("user not found"));
    //orElseGet(() -> ...) — only executes the code inside when the Optional IS empty. It's lazy — it only runs when needed.
    Cart cart = cartRepo.findByUser_Id(user.getId()).orElseGet(()-> {
        Cart newCart = new Cart();
        newCart.setUser(user);
        return cartRepo.save(newCart);
    });

    Product product =productRepo.findById(productId).orElseThrow(() -> new ResourceNotFountException("product not found!"));

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

public Cart getCartByUserEmail(String email) {
    User user = userRepo.findByEmail(email).orElseThrow(() -> new ResourceNotFountException("user not found"));
    return cartRepo.findByUser_Id(user.getId()).orElseThrow(() -> new ResourceNotFountException("cart not found!"));
}

public void updateCartIemQuantity(Long cardItemId , int quantity){
  cartItemRepo.findById(cardItemId).ifPresent(cartItem -> {
      cartItem.setQuantity(quantity);
      cartItemRepo.save(cartItem);
  });
}

public void removeItemFromCart(Long cartItemId){
    cartItemRepo.deleteById(cartItemId);

}
    public void clearCart(String email){
    User user = userRepo.findByEmail(email).orElseThrow(() -> new ResourceNotFountException("user not found"));
    cartRepo.findByUser_Id(user.getId()).ifPresent(cart -> {
        cartItemRepo.deleteAllByCart_Id(cart.getId());
    });
    }

}
