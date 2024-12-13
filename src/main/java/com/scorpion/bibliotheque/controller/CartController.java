package com.scorpion.bibliotheque.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.scorpion.bibliotheque.entites.Cart;
import com.scorpion.bibliotheque.services.CartService;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/{clientId}")
    public List<Cart> getCart(@PathVariable Long clientId) {
        return cartService.getCartByClientId(clientId);
    }

    @PostMapping
public ResponseEntity<Cart> addToCart(@RequestBody Cart cart) {
    try {
        Cart addedCart = cartService.addToCart(cart);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedCart);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body(null);  
    }
}

    @DeleteMapping("/{cartId}")
    public void removeFromCart(@PathVariable Long cartId) {
        cartService.removeFromCart(cartId);
    }
}

