package com.scorpion.bibliotheque.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scorpion.bibliotheque.entites.Adherent;
import com.scorpion.bibliotheque.entites.Emprunt;
import com.scorpion.bibliotheque.entites.Livre;
import com.scorpion.bibliotheque.repository.EmpruntRepository;
import com.scorpion.bibliotheque.repository.LivreRepository;
import com.scorpion.bibliotheque.repository.AdherentRepository;


import java.util.List;
import java.util.Optional;

@Service
public class EmpruntService {
    @Autowired
    private EmpruntRepository empruntRepository;

    @Autowired
    private LivreRepository livreRepository;

    @Autowired
    private AdherentRepository adherentRepository;

    public Emprunt ajouterEmprunt(Emprunt emprunt) {

        List<Emprunt> emprunts = empruntRepository.findByClientIdAndLivreId(emprunt.getClientId(), emprunt.getLivreId());

        if (!emprunts.isEmpty()) {
            throw new RuntimeException("Emprunt déjà existant pour le client ID: " + emprunt.getClientId() + " et le livre ID: " + emprunt.getLivreId());
        }

        if (emprunt == null || emprunt.getLivreId() == null || emprunt.getClientId() == null) {
            throw new IllegalArgumentException("Les informations de l'emprunt sont invalides");
        }

        Optional<Livre> optionalLivre = livreRepository.findById(emprunt.getLivreId());
        if (optionalLivre.isEmpty()) {
            throw new RuntimeException("Livre non trouvé avec l'ID: " + emprunt.getLivreId());
        }

        Livre livre = optionalLivre.get();
        if (livre.getQuantite() <= 0) {
            throw new RuntimeException("Livre non disponible : Quantité insuffisante");
        }

        

        List<Adherent> adherents = adherentRepository.findByClientId(emprunt.getClientId());
        if (adherents.isEmpty()) {
            throw new RuntimeException("Adhérent non trouvé pour le client ID: " + emprunt.getClientId());
        }

        Adherent adherent = adherents.get(adherents.size() - 1);
        if (adherent.getNbrEmprunt() <= 0) {
            throw new RuntimeException("Le nombre d'emprunts disponibles pour cet adhérent est épuisé");
        }

        livre.setQuantite(livre.getQuantite() - 1);
        livre.setNbrEmprunt(livre.getNbrEmprunt() + 1);
        livreRepository.save(livre);

        adherent.setNbrEmprunt(adherent.getNbrEmprunt() - 1);
        adherentRepository.save(adherent);

        return empruntRepository.save(emprunt);
    }


    public Optional<Emprunt> trouverEmpruntParId(Long id) {
        return empruntRepository.findById(id);
    }

    public List<Emprunt> listerTousLesEmprunts() {
        return empruntRepository.findAll();
    }

    public List<Emprunt> listerTousLesEmpruntsParClient(Long clientId) {
        return empruntRepository.findByClientId(clientId);
    }

    public Emprunt modifierEmprunt(Emprunt emprunt) {
        return empruntRepository.save(emprunt);
    }

    public void supprimerEmprunt(Long id) {
        // empruntRepository.deleteById(id);

        Emprunt emprunt = trouverEmpruntParId(id).orElseThrow(() -> new RuntimeException("Emprunt non trouvé avec l'ID: " + id));
        Livre livre = livreRepository.findById(emprunt.getLivreId()).orElseThrow(() -> new RuntimeException("Livre non trouvé avec l'ID: " + emprunt.getLivreId()));
        livre.setQuantite(livre.getQuantite() + 1);

        livreRepository.save(livre);
        empruntRepository.deleteById(id);
    }
}
