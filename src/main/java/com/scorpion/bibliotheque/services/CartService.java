package com.scorpion.bibliotheque.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scorpion.bibliotheque.entites.Cart;
import com.scorpion.bibliotheque.repository.CartRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    public List<Cart> getCartByClientId(Long clientId) {
        return cartRepository.findByClientId(clientId);
    }

    public Cart addToCart(Cart cart) {
        // Vérifier si le client a déjà ce livre dans son panier
        Optional<Cart> existingCart = cartRepository.findByClientIdAndLivreId(cart.getClientId(), cart.getLivreId());
        if (existingCart.isPresent()) {
            throw new IllegalArgumentException("Ce livre est déjà dans le panier du client.");
        }
    
        // Ajouter le livre au panier
        return cartRepository.save(cart);
    }
    

    public void removeFromCart(Long cartId) {
        cartRepository.deleteById(cartId);
    }
}

