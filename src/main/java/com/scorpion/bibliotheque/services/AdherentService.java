package com.scorpion.bibliotheque.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scorpion.bibliotheque.entites.Adherent;
import com.scorpion.bibliotheque.repository.AdherentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AdherentService {
    @Autowired
    private AdherentRepository adherentRepository;

    public Adherent ajouterAdherent(Adherent adherent) {
        return adherentRepository.save(adherent);
    }

    public Optional<Adherent> trouverAdherentParId(Long id) {
        return adherentRepository.findById(id);
    }

    public List<Adherent> listerTousLesAdherents() {
        return adherentRepository.findAll();
    }

    public Adherent modifierAdherent(Adherent adherent) {
        return adherentRepository.save(adherent);
    }

    public void supprimerAdherent(Long id) {
        adherentRepository.deleteById(id);
    }
}