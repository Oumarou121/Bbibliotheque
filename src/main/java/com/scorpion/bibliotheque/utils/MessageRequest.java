package com.scorpion.bibliotheque.utils;

public class MessageRequest {
    private String expediteur;
    private String message;

    public MessageRequest() {
    }
    
    public MessageRequest(String expediteur, String message) {
        this.expediteur = expediteur;
        this.message = message;
    }
    public String getExpediteur() {
        return expediteur;
    }
    public void setExpediteur(String expediteur) {
        this.expediteur = expediteur;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    
}
