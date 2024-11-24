package com.scorpion.bibliotheque.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.scorpion.bibliotheque.entites.Favorite;
import com.scorpion.bibliotheque.services.FavoriteService;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    @GetMapping("/{clientId}")
    public List<Favorite> getFavorites(@PathVariable Long clientId) {
        return favoriteService.getFavoritesByClientId(clientId);
    }

    @PostMapping
public ResponseEntity<Favorite> addToFavorite(@RequestBody Favorite favorite) {
    try {
        Favorite addedFavorite = favoriteService.addToFavorite(favorite);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedFavorite);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body(null);  // Vous pouvez aussi retourner un message d'erreur détaillé
    }
}

    @DeleteMapping("/{favoriteId}")
    public void removeFromFavorite(@PathVariable Long favoriteId) {
        favoriteService.removeFromFavorite(favoriteId);
    }
}

