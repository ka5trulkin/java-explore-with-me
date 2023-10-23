package ru.practicum.main.exception.child;

import ru.practicum.main.exception.base.DataConflictException;

import static ru.practicum.utils.message.ExceptionMessage.EVENT_PUBLISHING;

public class EventPublishingException extends DataConflictException {
    public EventPublishingException(Long eventId) {
        super(String.format(EVENT_PUBLISHING, eventId));
    }
}