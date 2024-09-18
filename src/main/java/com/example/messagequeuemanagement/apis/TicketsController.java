package com.example.messagequeuemanagement.apis;

import com.example.messagequeuemanagement.entities.Tickets;
import com.example.messagequeuemanagement.exceptions.MotifException;
import com.example.messagequeuemanagement.exceptions.TicketsException;
import com.example.messagequeuemanagement.records.TicketRecord;
import com.example.messagequeuemanagement.services.TicketsService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.*;
import java.util.List;

@RestController
@RequestMapping("v1/tickets")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TicketsController {
    TicketsService service;


    @PostMapping("create/web")
    public ResponseEntity<Boolean> createTicketWeb(@RequestBody TicketRecord ticketRecord) throws TicketsException, MotifException{
        service.createTicketWeb(ticketRecord);
        return ResponseEntity.ok(Boolean.TRUE.booleanValue());
    }

    @GetMapping("all/by/motif/{id}")
    public ResponseEntity<Tickets> getTicketToReceiveByMotif(@PathVariable("id") Long id) throws TicketsException{
        return ResponseEntity.ok(service.getTicketToReceiveByMotif(id));
    }
    @GetMapping("{id}")
    public ResponseEntity<Tickets> findTicketById(@PathVariable("id") Long id) throws TicketsException{
        return ResponseEntity.ok(service.findTicketById(id));
    }
    @PutMapping("{id}")
    public ResponseEntity<Boolean> closeTicket(@PathVariable("id") Long id) throws TicketsException{
        service.closeTicket(id);
        return ResponseEntity.ok(Boolean.TRUE);
    }

    @GetMapping("generate/{origin}")
    public ResponseEntity<String> findTicketById(@PathVariable("origin") String origin) throws TicketsException{
        return ResponseEntity.ok(service.generateUniqueString(origin));
    }
}
