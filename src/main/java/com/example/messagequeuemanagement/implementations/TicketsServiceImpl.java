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
import java.time.Instant;
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
    public List<Tickets> getTicketToReceiveByMotif(Instant start, Instant end, Long id) throws TicketsException {
        Motifs motifs = motifsRepository.getReferenceById(id);
        if(motifs==null)
            return new ArrayList<>(0);
        return repository.findTicketsByRecueOrderByDate(Boolean.FALSE.booleanValue(), id, start, end);
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

    }

    @Override
    public void transfertTicket(Long id, Long userId) throws TicketsException {

    }

    @Override
    public String generateUniqueString(String origin) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMddHHmmSSS");
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
                .comments(String.valueOf(' ').trim())
                .traiter(Boolean.FALSE.booleanValue())
                .transfert(Boolean.FALSE.booleanValue())
                .build();
        repository.save(tickets);
    }
}
