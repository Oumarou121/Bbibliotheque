package com.scorpion.bibliotheque.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scorpion.bibliotheque.entites.Adherent;

import java.util.List;


public interface AdherentRepository extends JpaRepository<Adherent, Long> {
    List<Adherent> findByClientId(Long clientId);
    void deleteByClientId(Long clientId);
}

