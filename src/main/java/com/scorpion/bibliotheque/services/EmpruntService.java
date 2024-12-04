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

    public Emprunt ajouterEmpruntM(Emprunt emprunt) {

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

        List<Adherent> adherents = adherentRepository.findByClientId(emprunt.getClientId());
        if (adherents.isEmpty()) {
            throw new RuntimeException("Adhérent non trouvé pour le client ID: " + emprunt.getClientId());
        }

        Adherent adherent = adherents.get(adherents.size() - 1);
        if (adherent.getNbrEmprunt() <= 0) {
            throw new RuntimeException("Le nombre d'emprunts disponibles pour cet adhérent est épuisé");
        }

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
        validateEmpruntData(emprunt);
    
        Emprunt lastEmprunt = empruntRepository.findById(emprunt.getId())
                .orElseThrow(() -> new RuntimeException("Emprunt avec ID non trouvé"));
    
        if (lastEmprunt.getClientId().equals(emprunt.getClientId()) && lastEmprunt.getLivreId().equals(emprunt.getLivreId())) {
            return empruntRepository.save(emprunt);
        }
    
        if (!lastEmprunt.getClientId().equals(emprunt.getClientId())) {
            Adherent adherent = getLatestAdherent(lastEmprunt.getClientId());
            adherent.setNbrEmprunt(adherent.getNbrEmprunt() + 1);
            adherentRepository.save(adherent);
            return ajouterEmpruntM(emprunt);
        }
    
        if (!lastEmprunt.getLivreId().equals(emprunt.getLivreId())) {
            Livre lastLivre = livreRepository.findById(lastEmprunt.getLivreId())
                    .orElseThrow(() -> new RuntimeException("Livre non trouvé avec l'ID: " + lastEmprunt.getLivreId()));
            Livre newLivre = livreRepository.findById(emprunt.getLivreId())
                    .orElseThrow(() -> new RuntimeException("Livre non trouvé avec l'ID: " + emprunt.getLivreId()));
    
            updateLivreStats(lastLivre, 1, -1);
            updateLivreStats(newLivre, -1, 1);
            return empruntRepository.save(emprunt);
        }
    
        return null;
    }
    

    // public Emprunt modifierEmprunt(Long id, Emprunt empruntDetails) {
    //     return empruntRepository.findById(id)
    //             .map(emprunt -> {
    //                 if (empruntDetails.getClientId() == null || empruntDetails.getLivreId() == null) {
    //                     throw new IllegalArgumentException("Les champs clientId et livreId sont obligatoires");
    //                 }
    //                 emprunt.setClientId(empruntDetails.getClientId());
    //                 emprunt.setLivreId(empruntDetails.getLivreId());
    //                 emprunt.setDateEmprunt(empruntDetails.getDateEmprunt());
    //                 emprunt.setDateRetourPrevue(empruntDetails.getDateRetourPrevue());
    //                 return empruntRepository.save(emprunt);
    //             })
    //             .orElseThrow(() -> new RuntimeException("Emprunt avec ID non trouvé"));
    // }
    

    public void supprimerEmprunt(Long id) {
        // empruntRepository.deleteById(id);

        Emprunt emprunt = trouverEmpruntParId(id).orElseThrow(() -> new RuntimeException("Emprunt non trouvé avec l'ID: " + id));
        Livre livre = livreRepository.findById(emprunt.getLivreId()).orElseThrow(() -> new RuntimeException("Livre non trouvé avec l'ID: " + emprunt.getLivreId()));
        livre.setQuantite(livre.getQuantite() + 1);

        livreRepository.save(livre);
        empruntRepository.deleteById(id);
    }

    private void validateEmpruntData(Emprunt emprunt) {
        if (emprunt == null || emprunt.getLivreId() == null || emprunt.getClientId() == null) {
            throw new IllegalArgumentException("Les informations de l'emprunt sont invalides");
        }
    }

    private void updateLivreStats(Livre livre, int quantiteChange, int nbrEmpruntChange) {
        livre.setQuantite(livre.getQuantite() + quantiteChange);
        livre.setNbrEmprunt(livre.getNbrEmprunt() + nbrEmpruntChange);
        livreRepository.save(livre);
    }

    private Adherent getLatestAdherent(Long clientId) {
        List<Adherent> adherents = adherentRepository.findByClientId(clientId);
        if (adherents.isEmpty()) {
            throw new RuntimeException("Adhérent non trouvé pour le client ID: " + clientId);
        }
        return adherents.get(adherents.size() - 1);
    }
    
}
