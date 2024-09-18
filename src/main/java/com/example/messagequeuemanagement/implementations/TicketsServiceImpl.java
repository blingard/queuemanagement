package com.example.messagequeuemanagement.implementations;

import com.example.messagequeuemanagement.entities.Motifs;
import com.example.messagequeuemanagement.entities.Tickets;
import com.example.messagequeuemanagement.enumerations.Sources;
import com.example.messagequeuemanagement.exceptions.MotifException;
import com.example.messagequeuemanagement.exceptions.TicketsException;
import com.example.messagequeuemanagement.records.TicketRecord;
import com.example.messagequeuemanagement.repositories.MotifsRepository;
import com.example.messagequeuemanagement.repositories.TicketRepository;
import com.example.messagequeuemanagement.repositories.UtilisateurRepository;
import com.example.messagequeuemanagement.services.TicketsService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TicketsServiceImpl implements TicketsService {
    TicketRepository repository;
    MotifsRepository motifsRepository;
    UtilisateurRepository utilisateurRepository;
    @Override
    public void createTicketMobile(TicketRecord ticketRecord) throws TicketsException, MotifException {
        save(ticketRecord, Sources.MOBILE);
    }

    @Override
    public void createTicketWeb(TicketRecord ticketRecord) throws TicketsException, MotifException {
        save(ticketRecord, Sources.WEBSITE);
    }

    @Override
    public void createTicketDevice(TicketRecord ticketRecord) throws TicketsException, MotifException {
        save(ticketRecord, Sources.LOCAL);
    }

    @Override
    public Tickets getTicketToReceiveByMotif(Long id) throws TicketsException {
        // Obtenir la date actuelle
        LocalDate today = LocalDate.now();
        // Obtenir le fuseau horaire par défaut du système
        ZoneId zoneId = ZoneId.systemDefault();

        // Créer un objet LocalDateTime pour l'heure de début de la journée (00:00)
        LocalDateTime startOfDay = today.atStartOfDay();
        // Créer un objet LocalDateTime pour l'heure de fin de la journée (23:59)
        LocalDateTime endOfDay = today.atTime(LocalTime.of(23, 59, 59));
        // Convertir les LocalDateTime en Instant
        Instant start = startOfDay.atZone(zoneId).toInstant();
        Instant end = endOfDay.atZone(zoneId).toInstant();
        /*Motifs motifs = motifsRepository.getReferenceById(id);
        if(motifs==null)
            throw new TicketsException("Not found", 404);*/

        List<Tickets> data = repository.findTicketsByCallingIsFalse().stream()
                .filter(tickets -> tickets.getDateTimeCreation().isAfter(start) && tickets.getDateTimeCreation().isBefore(end))
                .toList();
        if(data.isEmpty())
            throw new TicketsException("Not found", 404);
        Tickets tickets = data.get(0);
        tickets.setCalling(Boolean.TRUE);
        tickets = repository.save(tickets);
        return tickets;
    }

    @Override
    public List<Tickets> getTicketToReceiveByMotif() throws TicketsException {
        return repository.findAll();
    }

    @Override
    public Tickets findTicketById(Long id) throws TicketsException {
        Optional<Tickets> optionalTickets = repository.findTicketsById(id);
        if(optionalTickets.isEmpty())
            throw new TicketsException("Ticket not found",404);
        return optionalTickets.get();
    }

    @Override
    public void closeTicket(Long id) throws TicketsException {
        Optional<Tickets> optionalTickets = repository.findTicketsById(id);
        if(optionalTickets.isEmpty())
            throw new TicketsException("Ticket not found",404);
        Tickets tickets = optionalTickets.get();
        tickets.setComments("Traiter");
        tickets.setRecue(Boolean.TRUE.booleanValue());
        tickets.setCalling(Boolean.TRUE.booleanValue());
        repository.save(tickets);

    }

    @Override
    public void transfertTicket(Long id, Long userId) throws TicketsException {

    }

    @Override
    public String generateUniqueString(String origin) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMddHHmmssSSS");
        String timestamp = dateFormat.format(new Date());

        // Extraction des différentes parties du timestamp
        String monthDay = timestamp.substring(0, 4); // MMdd -> mois et jour
        String hourMinute = timestamp.substring(4, 8); // HHmm -> heure, minute et millisecondes
        String millis = timestamp.substring(8, 10); // SSS -> heure, minute et millisecondes

        // Combinaison du format W-MMdd-HHmmSSS
        String uniqueString = String.format(origin.trim().toUpperCase()+"-%s-%s-%s", monthDay, hourMinute, millis);

        return uniqueString;
    }

    private void save(TicketRecord ticketRecord, Sources sources) throws TicketsException, MotifException{
        Motifs motifs = motifsRepository.getReferenceById(ticketRecord.motifs());
        if(motifs == null)
            throw new MotifException("Motif with ID = "+ticketRecord.motifs()+" not found", 404);
        if(repository.findTicketsByIdentifiant(ticketRecord.identifiant()).isPresent())
            throw new TicketsException("Ticket with ID = "+ticketRecord.identifiant()+" Already exist", 417);
        Tickets tickets = Tickets.builder()
                .identifiant(ticketRecord.identifiant())
                .recue(Boolean.FALSE.booleanValue())
                .motifs(motifs)
                .source(sources)
                .dateTimeCreation(Instant.now())
                .comments(String.valueOf(' ').trim())
                .traiter(Boolean.FALSE.booleanValue())
                .transfert(Boolean.FALSE.booleanValue())
                .calling(Boolean.FALSE.booleanValue())
                .build();
        repository.save(tickets);
    }
}
