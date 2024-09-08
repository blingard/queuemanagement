package com.example.messagequeuemanagement.records;

import com.example.messagequeuemanagement.enumerations.UserType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Builder
public record UtilisateurRecord(
        String name,
        String login,
        UserType userType,
        LocalDateTime dateTimeCreation,
        boolean status
) {
}
