package com.scorpion.bibliotheque.controller;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

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
import com.scorpion.bibliotheque.entites.LivreDTO;
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

    @GetMapping("/{id}")
    public Optional<Livre> getLivreById(@PathVariable Long id) {
        return livreService.getLivreById(id);
    }
    

    // @PostMapping
    // public Livre createLivre(@RequestBody Livre livre) {
    //     return livreService.createLivre(livre);
    // }

    @PostMapping
    public Livre ajouterLivre(@RequestBody LivreDTO livreDTO){
        try{

            byte[] imageData = Base64.getDecoder().decode(livreDTO.getImage());
            return livreService.ajouterLivre(livreDTO.getTitre(), livreDTO.getAuteur(), livreDTO.getAnneePublication(), livreDTO.getQuantite(), livreDTO.getNbrEmprunt(), livreDTO.getDescription(),imageData);
             
        }catch (Exception e){
            return null;
        }
    }

    // @PutMapping("/{id}")
    // public Livre updateLivre(@PathVariable Long id, @RequestBody LivreDTO livreDetails) {
    //     Livre livre = new Livre(livreDetails.get);
    //     return livreService.updateLivre(id, livre);
    // }

    @PutMapping("/{id}")
    public Livre updateLivre(@PathVariable Long id, @RequestBody LivreDTO livreDTO) {
        byte[] imageData = Base64.getDecoder().decode(livreDTO.getImage());
        return livreService.updateLivre(id, livreDTO.getTitre(), livreDTO.getAuteur(), livreDTO.getAnneePublication(), livreDTO.getQuantite(), livreDTO.getNbrEmprunt(), livreDTO.getDescription(),imageData);
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
