package com.example.messagequeuemanagement.dtos;

import com.example.messagequeuemanagement.enumerations.Sources;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TicketDTO {
    Long id;
    String identifiant;
    Instant dateTimeCreation;
    Sources source;
    MotifsDTO motifs;
    boolean recue;
    boolean traiter;
    boolean transfert;
    String comments;
    UtilisateursDTO personelTraitant;
}
