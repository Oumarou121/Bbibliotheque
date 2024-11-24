package com.scorpion.bibliotheque.entites;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "client_id", nullable = false)
    private Long clientId;

    @Column(name = "livre_id", nullable = false)
    private Long livreId;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "added_at", updatable = false)
    private LocalDateTime addedAt = LocalDateTime.now();

    public Cart() {
    }

    public Cart(Long id, Long clientId, Long livreId, Integer quantity) {
        this.id = id;
        this.clientId = clientId;
        this.livreId = livreId;
        this.quantity = quantity;
        // this.addedAt = addedAt;
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(LocalDateTime addedAt) {
        this.addedAt = addedAt;
    }
}

