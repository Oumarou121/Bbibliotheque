package com.scorpion.bibliotheque.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.scorpion.bibliotheque.entites.Emprunt;
import com.scorpion.bibliotheque.services.EmpruntService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/emprunts")
public class EmpruntController {
    @Autowired
    private EmpruntService empruntService;

    @PostMapping
    public Emprunt ajouterEmprunt(@RequestBody Emprunt emprunt) {
        return empruntService.ajouterEmprunt(emprunt);
    }

    @GetMapping("/{id}")
    public Optional<Emprunt> trouverEmpruntParId(@PathVariable Long id) {
        return empruntService.trouverEmpruntParId(id);
    }

    @GetMapping
    public List<Emprunt> listerTousLesEmprunts() {
        return empruntService.listerTousLesEmprunts();
    }

    @PutMapping
    public Emprunt modifierEmprunt(@RequestBody Emprunt emprunt) {
        return empruntService.modifierEmprunt(emprunt);
    }

    @DeleteMapping("/{id}")
    public void supprimerEmprunt(@PathVariable Long id) {
        empruntService.supprimerEmprunt(id);
    }
}
