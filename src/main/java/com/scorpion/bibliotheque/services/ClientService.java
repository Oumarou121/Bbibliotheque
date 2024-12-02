package com.scorpion.bibliotheque.services;

import com.scorpion.bibliotheque.entites.Client;
import com.scorpion.bibliotheque.repository.AdherentRepository;
import com.scorpion.bibliotheque.repository.CartRepository;
import com.scorpion.bibliotheque.repository.ClientRepository;
import com.scorpion.bibliotheque.repository.EmpruntRepository;
import com.scorpion.bibliotheque.repository.FavoriteRepository;
import com.scorpion.bibliotheque.utils.JwtUtil;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final EmpruntRepository empruntRepository;
    private final AdherentRepository adherentRepository;
    private final CartRepository cartRepository;
    private final FavoriteRepository favoritesRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Autowired
    public ClientService(EmpruntRepository empruntRepository, AdherentRepository adherentRepository , CartRepository cartRepository, FavoriteRepository favoritesRepository, ClientRepository clientRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.clientRepository = clientRepository;
        this.empruntRepository = empruntRepository;
        this.adherentRepository = adherentRepository;
        this.cartRepository = cartRepository;
        this.favoritesRepository = favoritesRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // Enregistrement d'un nouveau client
    // public Client register(Client client) {
    //     // Vérification si l'email existe déjà
    //     Optional<Client> existingClient = clientRepository.findByEmail(client.getEmail());
    //     if (existingClient.isPresent()) {
    //         throw new RuntimeException("Cet email est déjà utilisé.");
    //     }

    //     // Encodage du mot de passe
    //     client.setPassword(passwordEncoder.encode(client.getPassword()));

    //     // Sauvegarde du client
    //     return clientRepository.save(client);
    // }

    public String register(Client client) {
        // Vérification si l'email existe déjà
        Optional<Client> existingClient = clientRepository.findByEmail(client.getEmail());
        if (existingClient.isPresent()) {
            throw new RuntimeException("Cet email est déjà utilisé.");
        }
    
        // Encodage du mot de passe
        client.setPassword(passwordEncoder.encode(client.getPassword()));
    
        // Sauvegarde du client
        Client savedClient = clientRepository.save(client);
    
        // Génération du token JWT
        return jwtUtil.generateToken(savedClient.getEmail());
    }
    

    // Connexion d'un client
    public String login(String email, String password) {
        // Vérification si l'utilisateur existe
        Client client = clientRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable."));

        // Vérification du mot de passe
        if (!passwordEncoder.matches(password, client.getPassword())) {
            throw new RuntimeException("Mot de passe incorrect.");
        }

        // Génération du token JWT
        return jwtUtil.generateToken(client.getEmail());
    }

    // Vérification du token pour les requêtes protégées
    public String validateToken(String token) {
        return jwtUtil.validateToken(token);
    }

    public Client getClientFromToken(String token) {
        // Validation du token et récupération de l'email (ou d'une autre information si vous le souhaitez)
        String email = jwtUtil.validateToken(token);
    
        if (email == null) {
            throw new RuntimeException("Token invalide ou expiré.");
        }
    
        // Récupérer le client par son email
        return clientRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable."));
    }
    
    @Transactional
    public Client updateClient(Long id, Client ClientDetails) {
        return clientRepository.findById(id)
                .map(client -> {
                    client.setNom(ClientDetails.getNom());
                    client.setAdresse(ClientDetails.getAdresse());
                    client.setTelephone(ClientDetails.getTelephone());
                    // client.setEmail(client.getEmail());
                    // client.setPassword(client.getPassword());
                    // client.setRole(client.getRole());
                    client.setEmail(ClientDetails.getEmail());
                    client.setPassword(client.getPassword());
                    client.setRole(ClientDetails.getRole());
                    return clientRepository.save(client);
                })
                .orElseThrow(() -> new RuntimeException("Client non trouvé"));
    }

    public List<Client> getAllClients(){
        return clientRepository.findAll();
    }
    
    @Transactional
    public void deleteClient(Long id){
        
        // Supprimer les emprunts, carts, favorites, adhérents associés au client
        empruntRepository.deleteByClientId(id);
        cartRepository.deleteByClientId(id);
        favoritesRepository.deleteByClientId(id);
        adherentRepository.deleteByClientId(id);

        clientRepository.deleteById(id);
    }
}
