package com.example.messagequeuemanagement.dtos;

import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FichiersVideoDTO {
    Long id;
    String name;
    String path;
    String size;
    String extension;
    LocalDateTime dateTimeCreation;
}
