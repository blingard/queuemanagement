package com.example.messagequeuemanagement.entities;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "motifs")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Motifs {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(nullable = false, unique = true)
    String name;

    @Column(nullable = false, unique = true)
    String initial;

    @CreationTimestamp
    LocalDateTime dateTimeCreation;

    @Column(columnDefinition = "boolean default false")
    boolean active;

    @ManyToOne
    Utilisateur createur;
}
