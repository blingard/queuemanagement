package com.example.messagequeuemanagement.repositories;

import com.example.messagequeuemanagement.entities.Motifs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MotifsRepository extends JpaRepository<Motifs, Long> {
    List<Motifs> findMotifsByActive(boolean status);
}
