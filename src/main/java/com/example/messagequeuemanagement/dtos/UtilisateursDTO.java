package com.example.messagequeuemanagement.dtos;

import com.example.messagequeuemanagement.enumerations.UserType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class UtilisateursDTO {
    Long id;
    @NotNull(message = "Le nom ne peut etre null")
    @NotEmpty(message = "Le nom ne peut etre vide")
    @NotBlank(message = "Le nom ne peut etre un espace vide")
    String name;
    @NotNull(message = "Le login ne peut etre null")
    @NotEmpty(message = "Le login ne peut etre vide")
    @NotBlank(message = "Le login ne peut etre un espace vide")
    String login;
    String password;
    @NotNull(message = "Choisir le type d'utilisateur")
    UserType userType;
    LocalDateTime dateTimeCreation;
    boolean status;
}
