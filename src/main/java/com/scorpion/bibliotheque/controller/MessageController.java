package com.scorpion.bibliotheque.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.scorpion.bibliotheque.services.MessageService;
import com.scorpion.bibliotheque.utils.MessageRequest;
import com.scorpion.bibliotheque.entites.Message;
import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @GetMapping
    public List<Message> getMesages(){
        return messageService.getAllMessage();
    }

    @GetMapping("/{recepteur}")
    public List<Message> getMesagesByRecepteur(@PathVariable String recepteur){
        return messageService.getMessagesByRecepteur(recepteur);
    }

    @DeleteMapping("/{id}")
    public void deleteMessage(@PathVariable Long id){
        messageService.deleteMessage(id);
    }    
    
    @PostMapping("/admin")
    public Message addAdminMessage(@RequestBody Message message){
        return messageService.ajouterAdminMessage(message);
    }

    @PostMapping
    public Message addClientMessage(@RequestBody MessageRequest message){
        return messageService.ajouterClientMessage(message.getExpediteur(), message.getMessage());
    }

}
