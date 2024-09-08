package com.example.messagequeuemanagement.exceptions;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class TicketsException extends Exception{
    String message;
    int code;

    public TicketsException(String message, int code) {
        super(message);
        this.message = message;
        this.code = code;
    }
}
