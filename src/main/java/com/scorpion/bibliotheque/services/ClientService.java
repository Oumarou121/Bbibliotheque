package com.scorpion.bibliotheque.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.scorpion.bibliotheque.entites.Client;
import com.scorpion.bibliotheque.repository.ClientRepository;

@Service
public class ClientService {
    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Client createClient(Client client) {
        return clientRepository.save(client);
    }

    public Client updateClient(Long id, Client ClientDetails) {
        return clientRepository.findById(id)
                .map(client -> {
                    client.setNom(ClientDetails.getNom());
                    client.setPrenom(ClientDetails.getPrenom());
                    client.setEmail(ClientDetails.getEmail());
                    client.setPassword(ClientDetails.getPassword());
                    return clientRepository.save(client);
                })
                .orElseThrow(() -> new RuntimeException("Client non trouvé"));
    }

    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }
}

