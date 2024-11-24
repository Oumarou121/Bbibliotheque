package com.scorpion.bibliotheque.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {

    private final String SECRET_KEY = "XyURE3YHpvO1GLlsNnWVvksSJZqvtP0NE6szYZXAcV/b+WIEuIUzzxYx1PmdC5I8JuoS8NQlAgbdVZa6efMpAg==";

    public JwtUtil() {
        // Vérifier que la clé secrète n'est pas vide ou null
        if (SECRET_KEY == null || SECRET_KEY.isEmpty()) {
            throw new IllegalStateException("La clé secrète JWT n'est pas définie.");
        }
    }

    // Génération du token JWT
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email) // Ajoutez l'email comme sujet
                .setIssuedAt(new Date(System.currentTimeMillis())) // Date de création
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // Expiration (10 heures)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY) // Algorithme de signature
                .compact();
    }

    // Extraction du sujet (email) depuis le token
    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extraction d'une réclamation personnalisée
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Validation du token
    public String validateToken(String token) {
        try {
            Claims claims = extractAllClaims(token);
            if (claims.getExpiration().before(new Date())) {
                throw new RuntimeException("Token expiré");
            }
            return claims.getSubject(); // Retourne l'email si le token est valide
        } catch (Exception e) {
            throw new RuntimeException("Token invalide : " + e.getMessage());
        }
    }

    // Extraction de toutes les réclamations
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY) // Utilisation de la clé secrète pour valider
                .parseClaimsJws(token)
                .getBody();
    }
}
