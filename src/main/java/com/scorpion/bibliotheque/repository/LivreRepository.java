package com.scorpion.bibliotheque.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scorpion.bibliotheque.entites.Livre;

public interface LivreRepository extends JpaRepository<Livre, Long> {

}

