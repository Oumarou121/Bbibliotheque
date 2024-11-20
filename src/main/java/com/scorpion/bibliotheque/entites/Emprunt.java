package com.scorpion.bibliotheque.entites;

import java.time.LocalDate;
import jakarta.persistence.*;

@Entity
@Table(name = "emprunt")
public class Emprunt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Livre livre;
    @ManyToOne
    private Adherent adherent;
    private LocalDate dateEmprunt;
    private LocalDate dateRetourPrevue;

    public Emprunt(){
        
    }

    public Emprunt(Long id, Livre livre, Adherent adherent, LocalDate dateEmprunt, LocalDate dateRetourPrevue) {
        this.id = id;
        this.livre = livre;
        this.adherent = adherent;
        this.dateEmprunt = dateEmprunt;
        this.dateRetourPrevue = dateRetourPrevue;
    }
    // Getters et setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Livre getLivre() {
        return livre;
    }
    public void setLivre(Livre livre) {
        this.livre = livre;
    }
    public Adherent getAdherent() {
        return adherent;
    }
    public void setAdherent(Adherent adherent) {
        this.adherent = adherent;
    }
    public LocalDate getDateEmprunt() {
        return dateEmprunt;
    }
    public void setDateEmprunt(LocalDate dateEmprunt) {
        this.dateEmprunt = dateEmprunt;
    }
    public LocalDate getDateRetourPrevue() {
        return dateRetourPrevue;
    }
    public void setDateRetourPrevue(LocalDate dateRetourPrevue) {
        this.dateRetourPrevue = dateRetourPrevue;
    }
    
}

