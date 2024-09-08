package com.example.messagequeuemanagement.exceptions;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class UtilisateurException extends Exception{
    String message;
    int code;

    public UtilisateurException(String message, int code) {
        super(message);
        this.message = message;
        this.code = code;
    }
}
