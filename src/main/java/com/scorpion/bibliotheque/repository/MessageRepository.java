package com.scorpion.bibliotheque.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scorpion.bibliotheque.entites.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByRecepteur(String recepteur);
}

