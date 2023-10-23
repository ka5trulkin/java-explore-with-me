package ru.practicum.stats.server.exception;


public class BadEventTimeException extends RuntimeException {
    public BadEventTimeException(String message) {
        super(message);
    }
}