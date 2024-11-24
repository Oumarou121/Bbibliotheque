package com.scorpion.bibliotheque.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.scorpion.bibliotheque.repository.InvalidTokenRepository;
import com.scorpion.bibliotheque.entites.InvalidToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class InvalidTokenService {

    @Autowired
    private InvalidTokenRepository invalidTokenRepository;
    
    private static final Logger logger = LoggerFactory.getLogger(InvalidTokenService.class);

    // Ajouter un token à la blacklist
    public void addInvalidToken(String token) {
        try {
            logger.info("Ajout du token invalide : {}", token);
            InvalidToken invalidToken = new InvalidToken(token);
            invalidTokenRepository.save(invalidToken);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de l'ajout du token invalide.", e);
        }
    }
    

    // Vérifier si un token est invalide
    public boolean isTokenInvalid(String token) {
        Optional<InvalidToken> invalidToken = invalidTokenRepository.findByToken(token);
        return invalidToken.isPresent();
    }

    // Supprimer les tokens expirés (automatiquement appelé tous les jours)
    @Scheduled(cron = "0 0 0 * * ?") // S'exécute tous les jours à minuit
    public void removeExpiredTokens() {
        LocalDateTime expirationDate = LocalDateTime.now().minusDays(30);
        invalidTokenRepository.deleteByCreatedAtBefore(expirationDate);
    }
}
