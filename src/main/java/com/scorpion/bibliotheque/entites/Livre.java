package com.scorpion.bibliotheque.entites;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "livre")
public class Livre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titre;
    private String auteur;
    private int anneePublication;
    private int quantite;
    private int nbrEmprunt;
    
    @Lob  
    private byte[] image;  
    
    @Column(columnDefinition = "TEXT")
    @Size(max = 65535, message = "La description ne peut pas dépasser 65535 caractères")
    private String description;


    public Livre() {
    }

    public Livre(Long id, String titre, String auteur, int anneePublication, int quantite, byte[] image, String description, int nbrEmprunt) {
        this.id = id;
        this.titre = titre;
        this.auteur = auteur;
        this.anneePublication = anneePublication;
        this.quantite = quantite;
        this.image = image;
        this.description = description;
        this.nbrEmprunt = nbrEmprunt;
    }

    // Getters et setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitre() {
        return titre;
    }
    public void setTitre(String titre) {
        this.titre = titre;
    }
    public String getAuteur() {
        return auteur;
    }
    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }
    public int getAnneePublication() {
        return anneePublication;
    }
    public void setAnneePublication(int anneePublication) {
        this.anneePublication = anneePublication;
    }
    
    public int getQuantite() {
        return quantite;
    }
    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public byte[] getImage() {
        return image;
    }
    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public int getNbrEmprunt() {
        return nbrEmprunt;
    }

    public void setNbrEmprunt(int nbrEmprunt) {
        this.nbrEmprunt = nbrEmprunt;
    }

    
}
