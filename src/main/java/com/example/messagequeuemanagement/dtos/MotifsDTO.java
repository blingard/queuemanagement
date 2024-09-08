package com.example.messagequeuemanagement.dtos;

import com.example.messagequeuemanagement.entities.Utilisateur;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MotifsDTO {
    Long id;
    @NotNull()
    @NotEmpty()
    @NotBlank()
    String name;
    LocalDateTime dateTimeCreation;
    boolean active;
    UtilisateursDTO createur;
}
