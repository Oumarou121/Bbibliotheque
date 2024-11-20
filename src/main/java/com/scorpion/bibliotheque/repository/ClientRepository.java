package com.scorpion.bibliotheque.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scorpion.bibliotheque.entites.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {

}

