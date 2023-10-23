package ru.practicum.main.exception.child;

import ru.practicum.main.exception.base.DataConflictException;

import static ru.practicum.utils.message.ExceptionMessage.EVENT_REQUEST_ALREADY_EXIST;

public class EventRequestAlreadyExistException extends DataConflictException {
    public EventRequestAlreadyExistException(Long eventId) {
        super(String.format(EVENT_REQUEST_ALREADY_EXIST, eventId));
    }
}