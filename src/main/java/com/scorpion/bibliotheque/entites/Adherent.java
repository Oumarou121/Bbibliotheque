package com.scorpion.bibliotheque.entites;

import java.time.LocalDate;
import jakarta.persistence.*;

@Entity
@Table(name = "adherent")
public class Adherent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long clientId;

    @Column(updatable = false)
    private LocalDate dateAdherent = LocalDate.now();

    @Column(updatable = false)
    private LocalDate dateFinAdherent = LocalDate.now().plusDays(30);

    private Long type;

    private Long nbrEmprunt;

    public Adherent(){
        
    }

    public Adherent(Long id, Long clientId, LocalDate dateAdherent, LocalDate dateFinAdherent, Long type, Long nbrEmprunt) {
        this.id = id;
        this.clientId = clientId;
        this.dateAdherent = dateAdherent;
        this.dateFinAdherent = dateFinAdherent;
        this.type = type;
        this.nbrEmprunt = nbrEmprunt;
    }
    // Getters et setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getClientId(){
        return clientId;
    }

    public void setClientId(Long clientId){
        this.clientId = clientId;
    }
    public LocalDate getDateAdherent() {
        return dateAdherent;
    }
    public void setDateAdherent(LocalDate dateAdherent) {
        this.dateAdherent = dateAdherent;
    }
    public LocalDate getDateFinAdherent() {
        return dateFinAdherent;
    }
    public void setDateFinAdherent(LocalDate dateFinAdherent) {
        this.dateFinAdherent = dateFinAdherent;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }
    
    public Long getNbrEmprunt() {
        return nbrEmprunt;
    }

    public void setNbrEmprunt(Long nbrEmprunt) {
        this.nbrEmprunt = nbrEmprunt;
    }
}

