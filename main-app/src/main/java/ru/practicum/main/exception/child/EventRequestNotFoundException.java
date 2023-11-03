package ru.practicum.main.exception.child;

import ru.practicum.main.exception.base.NotFoundException;

import static ru.practicum.utils.message.ExceptionMessage.EVENT_REQUEST_NOT_FOUND;

public class EventRequestNotFoundException extends NotFoundException {
    public EventRequestNotFoundException(Long eventId) {
        super(String.format(EVENT_REQUEST_NOT_FOUND, eventId));
    }
}