// package com.scorpion.bibliotheque.controller;

// import java.util.List;

// import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import jakarta.validation.Valid;

// import com.scorpion.bibliotheque.entites.Client;
// import com.scorpion.bibliotheque.services.ClientService;

// @RestController
// @RequestMapping("/api/client")
// public class ClientController {
//     private final ClientService clientService;

//     public ClientController(ClientService clientService) {
//         this.clientService = clientService;
//     }

//     @GetMapping
//     public List<Client> getAllClients() {
//         return clientService.getAllClients();
//     }

//     // @PostMapping
//     // public Client createClient(@RequestBody Client client) {
//     //     return clientService.createClient(client);
//     // }

//     @PostMapping("/register")
//     public ResponseEntity<?> register(@Valid @RequestBody Client client) {
//         Client registeredClient = clientService.register(client);
//         return ResponseEntity.ok(registeredClient);
//     }

//     @PostMapping("/login")
//     public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) {
//         Client client = clientService.login(email, password);
//         return ResponseEntity.ok(client);
//     }

//     @PutMapping("/{id}")
//     public Client updateClient(@PathVariable Long id, @RequestBody Client clientDetails) {
//         return clientService.updateClient(id, clientDetails);
//     }

//     @DeleteMapping("/{id}")
//     public void deleteClient(@PathVariable Long id) {
//         clientService.deleteClient(id);
//     }
// }



package com.scorpion.bibliotheque.controller;

import com.scorpion.bibliotheque.entites.Client;
import com.scorpion.bibliotheque.services.ClientService;
import com.scorpion.bibliotheque.services.InvalidTokenService;
import com.scorpion.bibliotheque.utils.LoginRequest;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/client")
public class ClientController {

    private final ClientService clientService;
    private final InvalidTokenService invalidTokenService;

    public ClientController(ClientService clientService, InvalidTokenService invalidTokenService) {
        this.clientService = clientService;
        this.invalidTokenService = invalidTokenService;
    }

    // Endpoint pour l'enregistrement d'un client
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody Client client) {
        try {
            String token = clientService.register(client);
            return ResponseEntity.ok().body("Bearer " + token);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/admin/register")
    public ResponseEntity<?> registerAdmin(@Valid @RequestBody Client client) {
        try {
            String token = clientService.register(client);
            return ResponseEntity.ok().body(client.getId());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Endpoint pour la connexion
    @PostMapping("/login")
public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
    try {
        String token = clientService.login(loginRequest.getEmail(), loginRequest.getPassword());
        return ResponseEntity.ok().body("Bearer " + token);
    } catch (RuntimeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}


    // Endpoint pour valider un token (facultatif, utile pour tester)
    @GetMapping("/validate-token")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String token) {
        try {
            String email = clientService.validateToken(token.replace("Bearer ", ""));
            return ResponseEntity.ok().body("Token valide pour l'utilisateur : " + email);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Endpoint pour la déconnexion (ajout du token à la liste des tokens invalidés)
    @PostMapping("/logout")
public ResponseEntity<?> logout(@RequestHeader("Authorization") String authorizationHeader) {
    // Vérification de l'en-tête Authorization
    if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
        return ResponseEntity.badRequest().body("Token manquant ou invalide.");
    }

    // Extraire le token
    String token = authorizationHeader.substring(7);

    // Vérifier si le token est déjà invalidé
    if (invalidTokenService.isTokenInvalid(token)) {
        return ResponseEntity.badRequest().body("Token déjà invalidé.");
    }

    // Ajouter le token à la liste noire
    invalidTokenService.addInvalidToken(token);

    return ResponseEntity.ok("Déconnexion réussie. Token invalidé.");
}

    @GetMapping("/me")
    public ResponseEntity<?> getClientInfo(@RequestHeader("Authorization") String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("Token manquant ou invalide.");
        }

        String token = authorizationHeader.substring(7); // Enlever le "Bearer " du token
        try {
            // Obtenez le client en validant le token
            Client client = clientService.getClientFromToken(token);
            return ResponseEntity.ok(client);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public Client updateClient(
            @PathVariable Long id, 
            @RequestBody Client clientDetails) {
        return clientService.updateClient(id, clientDetails);
    }

    @GetMapping
    public List<Client> getAllClients(){
        return clientService.getAllClients();
    }

    @DeleteMapping("/{id}")
    public void deleteClient(@PathVariable Long id){
        clientService.deleteClient(id);
    }

}
