package ru.gb.lk_loyality.exceptions;

import java.time.format.DateTimeParseException;

public class InvalidDateException extends RuntimeException {
    public InvalidDateException(String message) {
        super(message);
    }
}
