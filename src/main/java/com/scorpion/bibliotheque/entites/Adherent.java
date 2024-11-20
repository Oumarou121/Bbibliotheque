package com.scorpion.bibliotheque.entites;

import java.time.LocalDate;
import jakarta.persistence.*;

@Entity
@Table(name = "adherent")
public class Adherent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Client client;
    private LocalDate dateAdherent;
    private LocalDate dateFinAdherent;

    public Adherent(){
        
    }

    public Adherent(Long id, Client client, LocalDate dateAdherent, LocalDate dateFinAdherent) {
        this.id = id;
        this.client = client;
        this.dateAdherent = dateAdherent;
        this.dateFinAdherent = dateFinAdherent;
    }
    // Getters et setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    
    public Client getClient(){
        return client;
    }

    public void setClient(Client client){
        this.client = client;
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
    
}

