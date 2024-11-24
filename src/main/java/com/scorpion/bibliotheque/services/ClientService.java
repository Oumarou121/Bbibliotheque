// package com.scorpion.bibliotheque.services;

// import java.util.List;
// import java.util.Optional;

// import org.springframework.stereotype.Service;

// import com.scorpion.bibliotheque.entites.Client;
// import com.scorpion.bibliotheque.repository.ClientRepository;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.stereotype.Service;

// @Service
// public class ClientService {
//     private final ClientRepository clientRepository;

//     @Autowired
//     private PasswordEncoder passwordEncoder;

//     public ClientService(ClientRepository clientRepository) {
//         this.clientRepository = clientRepository;
//     }

//     public List<Client> getAllClients() {
//         return clientRepository.findAll();
//     }
    
//     // public Client createClient(Client client) {
//     //     return clientRepository.save(client);
//     // }

//     public Client register(Client client) {
//         // Vérifiez si l'email existe déjà
//         Optional<Client> existingUser = clientRepository.findByEmail(client.getEmail());
//         if (existingUser.isPresent()) {
//             throw new RuntimeException("Email déjà utilisé.");
//         }

//         // Encodez le mot de passe avant de sauvegarder
//         client.setPassword(passwordEncoder.encode(client.getPassword()));

//         return clientRepository.save(client);
//     }

//     public Client login(String email, String password) {
//         Client client = clientRepository.findByEmail(email)
//                 .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé."));

//         // Vérifiez si le mot de passe est correct
//         if (!passwordEncoder.matches(password, client.getPassword())) {
//             throw new RuntimeException("Mot de passe incorrect.");
//         }

//         return client;
//     }

//     public Client updateClient(Long id, Client ClientDetails) {
//         return clientRepository.findById(id)
//                 .map(client -> {
//                     client.setNom(ClientDetails.getNom());
//                     client.setPrenom(ClientDetails.getPrenom());
//                     client.setEmail(ClientDetails.getEmail());
//                     client.setPassword(ClientDetails.getPassword());
//                     return clientRepository.save(client);
//                 })
//                 .orElseThrow(() -> new RuntimeException("Client non trouvé"));
//     }

//     public void deleteClient(Long id) {
//         clientRepository.deleteById(id);
//     }
// }


package com.scorpion.bibliotheque.services;

import com.scorpion.bibliotheque.entites.Client;
import com.scorpion.bibliotheque.repository.ClientRepository;
import com.scorpion.bibliotheque.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Autowired
    public ClientService(ClientRepository clientRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.clientRepository = clientRepository;
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
    
}
