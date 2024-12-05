package com.scorpion.bibliotheque.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scorpion.bibliotheque.entites.Adherent;
import com.scorpion.bibliotheque.repository.AdherentRepository;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;


@Service
public class AdherentService {
    @Autowired
private AdherentRepository adherentRepository;

@Transactional
public Adherent ajouterAdherent(Adherent adherent) {
    if (adherent == null || adherent.getClientId() == null) {
        throw new IllegalArgumentException("L'adhérent ou son client ID est invalide.");
    }

    // Récupérer tous les adhérents pour le client
    List<Adherent> adherentAll = trouverAdherentsParClient(adherent.getClientId());

    if (adherentAll.isEmpty()) {
        // Aucun adhérent existant, on ajoute directement
        if (adherent.getType() == 1) {
            adherent.setNbrEmprunt((long) 5);
        }

        if (adherent.getType() == 2) {
            adherent.setNbrEmprunt((long)15);
        }
        
        if (adherent.getType() == 3) {
            adherent.setNbrEmprunt((long)35);
        }
        return adherentRepository.save(adherent);
    }

    // Récupérer le dernier adhérent existant
    Adherent lastAdherent = adherentAll.get(adherentAll.size() - 1);
    if (lastAdherent == null || lastAdherent.getDateFinAdherent() == null) {
        throw new IllegalStateException("Le dernier adhérent ou sa date de fin est invalide.");
    }

    LocalDate currentDate = LocalDate.now();
    LocalDate dateFinAdherent = lastAdherent.getDateFinAdherent();

    // Vérifier si un nouvel adhérent doit être créé
    if (currentDate.isAfter(dateFinAdherent) || lastAdherent.getNbrEmprunt() <= 0) {
        // Supprimer les anciens adhérents pour ce client
        adherentRepository.deleteByClientId(adherent.getClientId());

        if (adherent.getType() == 1) {
            adherent.setNbrEmprunt((long) 5);
        }

        if (adherent.getType() == 2) {
            adherent.setNbrEmprunt((long)15);
        }

        if (adherent.getType() == 3) {
            adherent.setNbrEmprunt((long)35);
        }

        // Créer un nouvel adhérent
        return adherentRepository.save(adherent);
    }

    // Retourner le dernier adhérent s'il est encore valide
    return lastAdherent;
}
   

    public Optional<Adherent> trouverAdherentParId(Long id) {
        return adherentRepository.findById(id);
    }

    public List<Adherent> listerTousLesAdherents() {
        return adherentRepository.findAll();
    }

    public List<Adherent> trouverAdherentsParClient(Long clientId){
        return adherentRepository.findByClientId(clientId);
    }

    public Adherent modifierAdherent(Adherent adherent) {
        return adherentRepository.save(adherent);
    }

    public void supprimerAdherent(Long id) {
        adherentRepository.deleteById(id);
    }

    @Transactional
    public void supprimerAdherentParClient(Long clientId) {
        adherentRepository.deleteByClientId(clientId);
    }
}