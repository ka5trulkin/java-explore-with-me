package ru.practicum.main.exception.child;

import ru.practicum.main.exception.base.NotFoundException;

import static ru.practicum.utils.message.ExceptionMessage.EVENT_NOT_FOUND;

public class EventNotFoundException extends NotFoundException {
    public EventNotFoundException(Long eventId) {
        super(String.format(EVENT_NOT_FOUND, eventId));
    }
}