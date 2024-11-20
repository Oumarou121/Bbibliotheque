package com.scorpion.bibliotheque.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.scorpion.bibliotheque.entites.Adherent;
import com.scorpion.bibliotheque.services.AdherentService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/adherents")
public class AdherentController {
    @Autowired
    private AdherentService adherentService;

    @PostMapping
    public Adherent ajouterAdherent(@RequestBody Adherent adherent) {
        return adherentService.ajouterAdherent(adherent);
    }

    @GetMapping("/{id}")
    public Optional<Adherent> trouverAdherentParId(@PathVariable Long id) {
        return adherentService.trouverAdherentParId(id);
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
}
