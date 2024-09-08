package com.example.messagequeuemanagement.repositories;

import com.example.messagequeuemanagement.entities.Utilisateur;
import com.example.messagequeuemanagement.enumerations.UserType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    Page<Utilisateur> findUtilisateursByUserTypeIsNot(UserType userType, Pageable pageable);

    Optional<Utilisateur> findUtilisateurByLogin(String login);
}
