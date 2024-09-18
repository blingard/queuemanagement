package com.example.messagequeuemanagement.entities;

import com.example.messagequeuemanagement.enumerations.Sources;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table(name = "tickets")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Tickets {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(nullable = false, unique = true)
    String identifiant;

    Instant dateTimeCreation;

    @Enumerated(EnumType.STRING)
    Sources source;

    @ManyToOne
    Motifs motifs;

    @Column(columnDefinition = "boolean default false")
    boolean recue;

    @Column(columnDefinition = "boolean default false")
    boolean calling;

    @Column(columnDefinition = "boolean default false")
    boolean traiter;

    @Column(columnDefinition = "boolean default false")
    boolean transfert;

    @Column(columnDefinition = "boolean default false")
    boolean abscent;

    @Column(length = 1000)
    String comments;

    @ManyToOne
    Utilisateur personelTraitant;


}
