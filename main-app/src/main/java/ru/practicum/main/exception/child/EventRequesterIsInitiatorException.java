package ru.practicum.main.exception.child;

import ru.practicum.main.exception.base.DataConflictException;

import static ru.practicum.utils.message.ExceptionMessage.EVENT_REQUEST_REQUESTER_IS_INITIATOR;

public class EventRequesterIsInitiatorException extends DataConflictException {
    public EventRequesterIsInitiatorException(Long eventId) {
        super(String.format(EVENT_REQUEST_REQUESTER_IS_INITIATOR, eventId));
    }
}