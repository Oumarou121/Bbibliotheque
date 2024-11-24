package com.scorpion.bibliotheque.utils;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.scorpion.bibliotheque.services.InvalidTokenService;

@Component
public class TokenCleanupTask {

    private final InvalidTokenService invalidTokenService;

    public TokenCleanupTask(InvalidTokenService invalidTokenService) {
        this.invalidTokenService = invalidTokenService;
    }

    @Scheduled(cron = "0 0 0 * * ?") // Exécution tous les jours à minuit
    public void cleanupExpiredTokens() {
        invalidTokenService.removeExpiredTokens();
    }
}

