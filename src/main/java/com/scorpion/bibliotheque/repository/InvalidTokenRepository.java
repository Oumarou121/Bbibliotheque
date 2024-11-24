package com.scorpion.bibliotheque.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scorpion.bibliotheque.entites.InvalidToken;

import java.time.LocalDateTime;
import java.util.Optional;

public interface InvalidTokenRepository extends JpaRepository<InvalidToken, Long> {
    Optional<InvalidToken> findByToken(String token);

    void deleteByCreatedAtBefore(LocalDateTime expirationDate);
}
