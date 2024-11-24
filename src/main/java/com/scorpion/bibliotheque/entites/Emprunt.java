package com.scorpion.bibliotheque.entites;

import java.time.LocalDate;
import jakarta.persistence.*;

@Entity
@Table(name = "emprunt")
public class Emprunt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 
    private Long clientId;

    private Long livreId;

    @Column(updatable = false)
    private LocalDate dateEmprunt = LocalDate.now();

    @Column(updatable = false)
    private LocalDate dateRetourPrevue = LocalDate.now().plusDays(7);

    public Emprunt(){
        
    }

    public Emprunt(Long id, Long clientId, Long livreId, LocalDate dateEmprunt, LocalDate dateRetourPrevue) {
        this.id = id;
        this.clientId = clientId;
        this.livreId = livreId;
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

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getLivreId() {
        return livreId;
    }

    public void setLivreId(Long livreId) {
        this.livreId = livreId;
    }
    
}

