package com.scorpion.bibliotheque.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.scorpion.bibliotheque.entites.Adherent;
import com.scorpion.bibliotheque.services.AdherentService;

import java.util.List;

@RestController
@RequestMapping("/api/adherents")
public class AdherentController {
    @Autowired
    private AdherentService adherentService;

    // @PostMapping
    // public Adherent ajouterAdherent(@RequestBody Adherent adherent) {
    //     return adherentService.ajouterAdherent(adherent);
    // }

    @PostMapping
    public ResponseEntity<Adherent> ajouterAdherent(@RequestBody Adherent adherent) {
        if (adherent == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        try {
            Adherent newAdherent = adherentService.ajouterAdherent(adherent);

            return new ResponseEntity<>(newAdherent, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // @GetMapping("/{id}")
    // public Optional<Adherent> trouverAdherentParId(@PathVariable Long id) {
    //     return adherentService.trouverAdherentParId(id);
    // }

    @GetMapping("/{id}")
    public List<Adherent> trouverAdherentParClient(@PathVariable Long id) {
        return adherentService.trouverAdherentsParClient(id);
    }

    @GetMapping
    public List<Adherent> listerTousLesAdherents() {
        return adherentService.listerTousLesAdherents();
    }

    @PutMapping
    public Adherent modifierAdherent(@RequestBody Adherent adherent) {
        return adherentService.modifierAdherent(adherent);
    }

    @DeleteMapping("/{id}")
    public void supprimerAdherent(@PathVariable Long id) {
        adherentService.supprimerAdherent(id);
    }

    @DeleteMapping("/client/{clientId}")
    public ResponseEntity<Void> deleteAdherentsByClientId(@PathVariable Long clientId) {
        adherentService.supprimerAdherentParClient(clientId);
        return ResponseEntity.noContent().build();
    }
}
