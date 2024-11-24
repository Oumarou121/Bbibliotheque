package com.scorpion.bibliotheque.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

import com.scorpion.bibliotheque.entites.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findByClientId(Long clientId);

    Optional<Cart> findByClientIdAndLivreId(Long clientId, Long livreId);
}

