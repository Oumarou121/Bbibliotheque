package com.scorpion.bibliotheque.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scorpion.bibliotheque.entites.Favorite;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    List<Favorite> findByClientId(Long clientId);

    Optional<Favorite> findByClientIdAndLivreId(Long clientId, Long livreId);

}

