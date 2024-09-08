package com.example.messagequeuemanagement.services;

import com.example.messagequeuemanagement.dtos.TicketDTO;
import com.example.messagequeuemanagement.entities.Tickets;
import com.example.messagequeuemanagement.exceptions.MotifException;
import com.example.messagequeuemanagement.exceptions.TicketsException;
import com.example.messagequeuemanagement.exceptions.UtilisateurException;
import com.example.messagequeuemanagement.records.TicketRecord;

import java.time.Instant;
import java.util.List;

public interface TicketsService {
    void createTicketMobile(TicketRecord ticketRecord) throws TicketsException, MotifException;
    void createTicketWeb(TicketRecord ticketRecord) throws TicketsException, MotifException;
    void createTicketDevice(TicketRecord ticketRecord) throws TicketsException, MotifException;
    List<Tickets> getTicketToReceiveByMotif(Instant start, Instant end, Long id) throws TicketsException;
    Tickets findTicketById(Long id) throws TicketsException;
    void closeTicket(Long id) throws TicketsException;
    void transfertTicket(Long id, Long userId) throws TicketsException;
    String generateUniqueString(String origin);
}
