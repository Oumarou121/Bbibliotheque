package com.scorpion.bibliotheque.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.scorpion.bibliotheque.entites.Emprunt;
import com.scorpion.bibliotheque.services.EmpruntService;

import java.util.List;

@RestController
@RequestMapping("/api/emprunts")
public class EmpruntController {
    @Autowired
    private EmpruntService empruntService;

    @PostMapping
    public ResponseEntity<?> ajouterEmprunt(@RequestBody Emprunt emprunt) {
        try {
            Emprunt nouvelEmprunt = empruntService.ajouterEmprunt(emprunt);
            return ResponseEntity.status(HttpStatus.CREATED).body(nouvelEmprunt);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur inattendue : " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public List<Emprunt> trouverEmpruntParId(@PathVariable Long id) {
        return empruntService.listerTousLesEmpruntsParClient(id);
    }

    @GetMapping
    public List<Emprunt> listerTousLesEmprunts() {
        return empruntService.listerTousLesEmprunts();
    }

    @PutMapping
    public Emprunt modifierEmprunt(@RequestBody Emprunt emprunt) {
        return empruntService.modifierEmprunt(emprunt);
    }

//     @PutMapping("/{id}")
// public ResponseEntity<Emprunt> modifierEmprunt(@PathVariable Long id, @RequestBody Emprunt emprunt) {
//     try {
//         Emprunt updatedEmprunt = empruntService.modifierEmprunt(id, emprunt);
//         return ResponseEntity.ok(updatedEmprunt);
//     } catch (IllegalArgumentException ex) {
//         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//     } catch (Exception ex) {
//         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//     }
// }


    @DeleteMapping("/{id}")
    public void supprimerEmprunt(@PathVariable Long id) {
        empruntService.supprimerEmprunt(id);
    }
    
}
