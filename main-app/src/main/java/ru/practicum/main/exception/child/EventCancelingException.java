package ru.practicum.main.exception.child;

import ru.practicum.main.exception.base.DataConflictException;

import static ru.practicum.utils.message.ExceptionMessage.EVENT_CANCELING;

public class EventCancelingException extends DataConflictException {
    public EventCancelingException(Long eventId) {
        super(String.format(EVENT_CANCELING, eventId));
    }
}