package com.scorpion.bibliotheque.services;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Service
public class BookService {
    private final WebClient webClient;

    public BookService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://www.googleapis.com/books/v1").build();
    }
    
    public String fetchBookDetails(String title, String author) {
        try {
            // Encoder le titre et l'auteur pour l'URL
            String encodedTitle = URLEncoder.encode(title, StandardCharsets.UTF_8.toString());
            String encodedAuthor = URLEncoder.encode(author, StandardCharsets.UTF_8.toString());

            // Construire la requête de recherche
            String query = String.format("q=intitle:%s+inauthor:%s", encodedTitle, encodedAuthor);

            // Faire la requête GET
            Map<String, Object> response = this.webClient.get()
                    .uri("/volumes?" + query)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            // Vérifier si des livres ont été trouvés
            if (response != null && response.containsKey("items")) {
                List<Map<String, Object>> items = (List<Map<String, Object>>) response.get("items");
                if (!items.isEmpty()) {
                    // Récupérer les informations du premier livre
                    Map<String, Object> volumeInfo = (Map<String, Object>) items.get(0).get("volumeInfo");

                    // Récupérer la description du livre (si elle existe)
                    String description = (String) volumeInfo.getOrDefault("description", "Description non disponible.");

                    // Récupérer le previewLink ou, à défaut, l'infoLink
                    String previewLink = (String) volumeInfo.get("previewLink");
                    String linkToReturn = previewLink != null ? previewLink : (String) volumeInfo.get("infoLink");

                    // Retourner la description et le lien
                    return "Description: " + description + "\n" + "Lien pour plus d'infos: " + linkToReturn;
                }
            }
        } catch (Exception e) {
            System.err.println("Error fetching book details: " + e.getMessage());
        }
        return "Aucun livre trouvé ou une erreur est survenue.";
    }
}

