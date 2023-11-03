package ru.practicum.main.exception.child;

import ru.practicum.main.exception.base.DataConflictException;

import static ru.practicum.utils.message.ExceptionMessage.EVENT_UPDATE_STATUS_REQUEST;

public class EventUpdateStatusRequestException extends DataConflictException {
    public EventUpdateStatusRequestException(Long eventId) {
        super(String.format(EVENT_UPDATE_STATUS_REQUEST, eventId));
    }
}