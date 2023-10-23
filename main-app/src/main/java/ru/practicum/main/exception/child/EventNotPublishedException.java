package ru.practicum.main.exception.child;

import ru.practicum.main.exception.base.DataConflictException;

import static ru.practicum.utils.message.ExceptionMessage.EVENT_NOT_PUBLISHED_EXIST;

public class EventNotPublishedException extends DataConflictException {
    public EventNotPublishedException(Long eventId) {
        super(String.format(EVENT_NOT_PUBLISHED_EXIST, eventId));
    }
}