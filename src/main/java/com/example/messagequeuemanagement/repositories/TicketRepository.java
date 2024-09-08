package com.example.messagequeuemanagement.repositories;

import com.example.messagequeuemanagement.entities.Motifs;
import com.example.messagequeuemanagement.entities.Tickets;
import com.example.messagequeuemanagement.entities.Utilisateur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface TicketRepository extends JpaRepository<Tickets, Long> {
    Page<Tickets> findTicketsByRecueAndMotifs(boolean recue, Motifs motifs, Pageable pageable);
    List<Tickets> findTicketsByRecue(boolean recue);

    @Query("select t from Tickets t where t.recue = :recue and t.traiter = false and t.transfert = false "
            +"and t.dateTimeCreation >= :start and t.motifs.id = :motif "
                    +"and t.dateTimeCreation <= :end")
    List<Tickets> findTicketsByRecueOrderByDate(
            @Param("recue") boolean recus,
            @Param("motif") Long motif,
            @Param("start") Instant start,
            @Param("end") Instant end);
    Page<Tickets> findTicketsByTransfertAndMotifs(boolean transfert, Motifs motifs, Pageable pageable);
    //List<Tickets> findTicketsByTransfert();
    List<Tickets> findTicketsByTransfert(boolean transfert);

    Page<Tickets> findTicketsByTraiterAndMotifs(boolean traiter, Motifs motifs, Pageable pageable);
    //List<Tickets> findTicketsByTraiter();
    List<Tickets> findTicketsByTraiter(boolean traiter);
    Optional<Tickets> findTicketsByIdentifiant(String identifiant);
    Optional<Tickets> findTicketsById(Long id);
}
