package com.scorpion.bibliotheque.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.scorpion.bibliotheque.entites.Livre;
import com.scorpion.bibliotheque.repository.LivreRepository;

@Service
public class LivreService {
    private final LivreRepository livreRepository;

    public LivreService(LivreRepository livreRepository) {
        this.livreRepository = livreRepository;
    }

    public List<Livre> getAllLivres() {
        return livreRepository.findAll();
    }

    public Optional<Livre> getLivreById(Long id) {
        return livreRepository.findById(id);
    }

    public Livre createLivre(Livre livre) {
        return livreRepository.save(livre);
    }

    public Livre ajouterLivre(String titre, String auteur, int anneePublication, int quantite, int nbrEmprunt, String description,
    byte[] image){
        Livre livre = new Livre();
        livre.setTitre(titre);
        livre.setAuteur(auteur);
        livre.setAnneePublication(anneePublication);
        livre.setQuantite(quantite);
        livre.setNbrEmprunt(nbrEmprunt);
        livre.setDescription(description);
        livre.setImage(image);

        return livreRepository.save(livre);
    }

    public Livre updateLivre(Long id, String titre, String auteur, int anneePublication, int quantite, int nbrEmprunt, String description,
    byte[] image) {
        return livreRepository.findById(id)
                .map(livre -> {
                    livre.setTitre(titre);
                    livre.setAuteur(auteur);
                    livre.setAnneePublication(anneePublication);
                    livre.setQuantite(quantite);
                    livre.setDescription(description);
                    livre.setImage(image);
                    livre.setNbrEmprunt(nbrEmprunt);
                    return livreRepository.save(livre);
                })
                .orElseThrow(() -> new RuntimeException("Livre non trouvé"));
    }

    // public Livre updateLivre(Long id, Livre livreDetails) {
    //     return livreRepository.findById(id)
    //             .map(livre -> {
    //                 livre.setTitre(livreDetails.getTitre());
    //                 livre.setAuteur(livreDetails.getAuteur());
    //                 livre.setAnneePublication(livreDetails.getAnneePublication());
    //                 livre.setQuantite(livreDetails.getQuantite());
    //                 livre.setDescription(livreDetails.getDescription());
    //                 livre.setImage(livreDetails.getImage());
    //                 livre.setNbrEmprunt(livreDetails.getNbrEmprunt());
    //                 return livreRepository.save(livre);
    //             })
    //             .orElseThrow(() -> new RuntimeException("Livre non trouvé"));
    // }

    public void deleteLivre(Long id) {
        livreRepository.deleteById(id);
    }

    public byte[] getLivreImage(Long id) {
        Livre livre = livreRepository.findById(id).orElse(null);
        if (livre != null && livre.getImage() != null) {
            return livre.getImage(); // `image` est supposé être un champ de type byte[]
        }
        return null;
    }

    public String getTitreById(Long id){
        return livreRepository.findById(id)
        .map(Livre::getTitre)
        .orElseThrow(() -> new RuntimeException("Id invalide"));
    }
}

