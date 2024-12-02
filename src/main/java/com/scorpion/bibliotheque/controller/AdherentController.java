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
public ResponseEntity<?> ajouterAdherent(@RequestBody Adherent adherent) {
    if (adherent == null) {
        return ResponseEntity.badRequest().body("Les informations de l'adhérent sont invalides.");
    }

    try {
        Adherent newAdherent = adherentService.ajouterAdherent(adherent);
        return new ResponseEntity<>(newAdherent, HttpStatus.CREATED);

    } catch (IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());

    } catch (IllegalStateException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());

    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Une erreur interne s'est produite.");
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
