package com.example.messagequeuemanagement.exceptions;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class FileException extends Exception{
    String message;
    int code;

    public FileException(String message, int code) {
        super(message);
        this.message = message;
        this.code = code;
    }
}
