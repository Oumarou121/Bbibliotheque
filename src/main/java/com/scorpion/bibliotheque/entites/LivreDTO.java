package com.scorpion.bibliotheque.entites;

public class LivreDTO{
    private String titre;
    private String auteur;
    private int anneePublication;
    private int quantite;
    private int nbrEmprunt;
    private String description;
    private String image;

    public LivreDTO(String titre, String auteur, int anneePublication, int quantite, int nbrEmprunt, String description,
            String image) {
        this.titre = titre;
        this.auteur = auteur;
        this.anneePublication = anneePublication;
        this.quantite = quantite;
        this.nbrEmprunt = nbrEmprunt;
        this.description = description;
        this.image = image;
    }
    public LivreDTO() {
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
    public int getNbrEmprunt() {
        return nbrEmprunt;
    }
    public void setNbrEmprunt(int nbrEmprunt) {
        this.nbrEmprunt = nbrEmprunt;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }

    
}