package com.scorpion.bibliotheque.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scorpion.bibliotheque.entites.Emprunt;
import com.scorpion.bibliotheque.repository.EmpruntRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EmpruntService {
    @Autowired
    private EmpruntRepository empruntRepository;

    public Emprunt ajouterEmprunt(Emprunt emprunt) {
        return empruntRepository.save(emprunt);
    }

    public Optional<Emprunt> trouverEmpruntParId(Long id) {
        return empruntRepository.findById(id);
    }

    public List<Emprunt> listerTousLesEmprunts() {
        return empruntRepository.findAll();
    }

    public Emprunt modifierEmprunt(Emprunt emprunt) {
        return empruntRepository.save(emprunt);
    }

    public void supprimerEmprunt(Long id) {
        empruntRepository.deleteById(id);
    }
}
