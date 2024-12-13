package com.scorpion.bibliotheque.controller;

import com.scorpion.bibliotheque.entites.Client;
import com.scorpion.bibliotheque.services.ClientService;
import com.scorpion.bibliotheque.services.InvalidTokenService;
import com.scorpion.bibliotheque.utils.LoginRequest;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/api/client")
public class ClientController {

    private final ClientService clientService;
    private final InvalidTokenService invalidTokenService;

    public ClientController(ClientService clientService, InvalidTokenService invalidTokenService) {
        this.clientService = clientService;
        this.invalidTokenService = invalidTokenService;
    }

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
            clientService.registerAdmin(client);
            return ResponseEntity.ok().body(client.getId());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
    try {
        String token = clientService.login(loginRequest.getEmail(), loginRequest.getPassword());
        return ResponseEntity.ok().body("Bearer " + token);
    } catch (RuntimeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}


    @GetMapping("/validate-token")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String token) {
        try {
            String email = clientService.validateToken(token.replace("Bearer ", ""));
            return ResponseEntity.ok().body("Token valide pour l'utilisateur : " + email);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/logout")
public ResponseEntity<?> logout(@RequestHeader("Authorization") String authorizationHeader) {
    if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
        return ResponseEntity.badRequest().body("Token manquant ou invalide.");
    }

    String token = authorizationHeader.substring(7);

    if (invalidTokenService.isTokenInvalid(token)) {
        return ResponseEntity.badRequest().body("Token déjà invalidé.");
    }

    invalidTokenService.addInvalidToken(token);

    return ResponseEntity.ok("Déconnexion réussie. Token invalidé.");
}

    @GetMapping("/me")
    public ResponseEntity<?> getClientInfo(@RequestHeader("Authorization") String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("Token manquant ou invalide.");
        }

        String token = authorizationHeader.substring(7);
        try {
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

    @GetMapping("/email/{id}")
    public String getEmailById(@PathVariable Long id) {
        return clientService.getEmailById(id);
    }
    

}
