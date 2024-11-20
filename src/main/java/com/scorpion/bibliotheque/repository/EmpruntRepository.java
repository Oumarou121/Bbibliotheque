package com.scorpion.bibliotheque.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scorpion.bibliotheque.entites.Emprunt;

public interface EmpruntRepository extends JpaRepository<Emprunt, Long> {

}

