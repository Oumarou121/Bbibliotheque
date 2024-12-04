package com.scorpion.bibliotheque.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scorpion.bibliotheque.repository.MessageRepository;
import com.scorpion.bibliotheque.entites.Message;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;
    
    public List<Message> getAllMessage(){
        return messageRepository.findAll();
    }

    public List<Message> getMessagesByRecepteur(String recepteur){
        List<Message> liste1 =  messageRepository.findByRecepteur(recepteur);
        List<Message> liste2 =  messageRepository.findByRecepteur("all");
        List<Message> liste = new ArrayList<Message>();
        liste.addAll(liste1);
        liste.addAll(liste2);
        return liste;
    }

    public Message ajouterAdminMessage(Message message){
        return messageRepository.save(message);
    }

    public Message ajouterClientMessage(String expediteur, String message){
        String adminEmail = "oumaroumamodou123@gmail.com";
        Message current = new Message();
        current.setExpediteur(expediteur);
        current.setRecepteur(adminEmail);
        current.setMessage(message);
        current.setAdmin(false);
        return messageRepository.save(current);

    }

    public void deleteMessage(Long id){
        messageRepository.deleteById(id);
    }
    
}
