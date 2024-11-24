package com.scorpion.bibliotheque.entites;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "favorite")
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "client_id", nullable = false)
    private Long clientId;

    @Column(name = "livre_id", nullable = false)
    private Long livreId;

    @Column(name = "added_at", updatable = false)
    private LocalDateTime addedAt = LocalDateTime.now();

    public Favorite() {
    }

    public Favorite(Long id, Long clientId, Long livreId, LocalDateTime addedAt) {
        this.id = id;
        this.clientId = clientId;
        this.livreId = livreId;
        this.addedAt = addedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDateTime getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(LocalDateTime addedAt) {
        this.addedAt = addedAt;
    }

    // Getters and Setters
    
}

