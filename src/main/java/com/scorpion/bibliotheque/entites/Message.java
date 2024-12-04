package com.scorpion.bibliotheque.entites;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String expediteur;

    @Column(nullable = false)
    private String recepteur;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private boolean admin;

    @Column(nullable = false, updatable = false)
    private String timestamp;

    public Message() {
    }

    public Message(long id, String expediteur, String recepteur, String message, boolean admin) {
        this.id = id;
        this.expediteur = expediteur;
        this.recepteur = recepteur;
        this.message = message;
        this.admin = admin;
    }

    @PrePersist
    private void setCreationTimestamp() {
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getExpediteur() {
        return expediteur;
    }

    public void setExpediteur(String expediteur) {
        this.expediteur = expediteur;
    }

    public String getRecepteur() {
        return recepteur;
    }

    public void setRecepteur(String recepteur) {
        this.recepteur = recepteur;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean getAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
