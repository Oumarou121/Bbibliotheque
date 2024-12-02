package com.scorpion.bibliotheque.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scorpion.bibliotheque.entites.Emprunt;
import java.util.List;


public interface EmpruntRepository extends JpaRepository<Emprunt, Long> {
    List<Emprunt> findByClientIdAndLivreId(Long clientId, Long livreId);
    List<Emprunt> findByClientId(Long clientId);
    void deleteByClientId(Long id);
}

