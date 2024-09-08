package com.example.messagequeuemanagement.exceptions;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class MotifException extends Exception{
    String message;
    int code;

    public MotifException(String message, int code) {
        super(message);
        this.message = message;
        this.code = code;
    }
}
