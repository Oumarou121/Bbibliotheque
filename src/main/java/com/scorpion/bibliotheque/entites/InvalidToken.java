package com.scorpion.bibliotheque.entites;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "invalid_tokens")
public class InvalidToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public InvalidToken() {
        this.createdAt = LocalDateTime.now();
    }

    public InvalidToken(String token) {
        this.token = token;
        this.createdAt = LocalDateTime.now();
    }

    // Getters et setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
