package com.scorpion.bibliotheque.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scorpion.bibliotheque.entites.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByEmail(String email);
}

