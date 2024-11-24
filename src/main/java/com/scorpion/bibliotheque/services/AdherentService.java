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

    public Adherent ajouterAdherent(Adherent adherent) {
        List<Adherent> adherentAll = trouverAdherentsParClient(adherent.getClientId());
        
        if (adherentAll.isEmpty()) {
            return adherentRepository.save(adherent);
        } else {
            Adherent lastAdherent = adherentAll.get(adherentAll.size() - 1);  
            
            if (lastAdherent != null && lastAdherent.getDateFinAdherent() != null) {
                LocalDate currentDate = LocalDate.now();
                LocalDate dateFinAdherent = lastAdherent.getDateFinAdherent();
    
                if (currentDate.isAfter(dateFinAdherent) || lastAdherent.getNbrEmprunt() == 0) {
                    adherentRepository.deleteByClientId(adherent.getClientId());
                    return adherentRepository.save(adherent);
                }
            } else {

                System.err.println("Le dernier adhérent ou la date de fin d'adhésion est invalide.");
                return null;
            }
        }
    
        return null; 
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