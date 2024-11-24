package com.scorpion.bibliotheque.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scorpion.bibliotheque.entites.Favorite;
import com.scorpion.bibliotheque.repository.FavoriteRepository;

import java.util.List;
import java.util.Optional;

@Service
public class FavoriteService {

    @Autowired
    private FavoriteRepository favoriteRepository;

    public List<Favorite> getFavoritesByClientId(Long clientId) {
        return favoriteRepository.findByClientId(clientId);
    }

    public Favorite addToFavorite(Favorite favorite) {
        // Vérifier si le client a déjà ce livre dans ses favoris
        Optional<Favorite> existingFavorite = favoriteRepository.findByClientIdAndLivreId(favorite.getClientId(), favorite.getLivreId());
        if (existingFavorite.isPresent()) {
            throw new IllegalArgumentException("Ce livre est déjà dans les favoris du client.");
        }

        // Ajouter le livre aux favoris
        return favoriteRepository.save(favorite);
    }

    public void removeFromFavorite(Long favoriteId) {
        favoriteRepository.deleteById(favoriteId);
    }
}

