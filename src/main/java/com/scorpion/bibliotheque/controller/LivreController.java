package com.scorpion.bibliotheque.controller;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scorpion.bibliotheque.entites.Livre;
import com.scorpion.bibliotheque.services.LivreService;

@RestController
@RequestMapping("/api/livres")
public class LivreController {

    private final LivreService livreService;

    public LivreController(LivreService livreService) {
        this.livreService = livreService;
    }

    @GetMapping
    public List<Livre> getAllLivres() {
        return livreService.getAllLivres();
    }

    @PostMapping
    public Livre createLivre(@RequestBody Livre livre) {
        return livreService.createLivre(livre);
    }

    @PutMapping("/{id}")
    public Livre updateLivre(@PathVariable Long id, @RequestBody Livre livreDetails) {
        return livreService.updateLivre(id, livreDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteLivre(@PathVariable Long id) {
        livreService.deleteLivre(id);
    }

    // Endpoint pour récupérer l'image d'un livre
    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getLivreImage(@PathVariable Long id) {
        byte[] image = livreService.getLivreImage(id);
        if (image == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "image/jpg");  // Changez le type si l'image n'est pas JPEG
        return new ResponseEntity<>(image, headers, HttpStatus.OK);
    }
}
