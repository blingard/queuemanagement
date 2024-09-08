package com.example.messagequeuemanagement.records;

import com.example.messagequeuemanagement.enumerations.Sources;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

public record TicketRecord(
        String identifiant,
        Date dateTimeCreation,
        Sources source,
        Long motifs
) {
}
