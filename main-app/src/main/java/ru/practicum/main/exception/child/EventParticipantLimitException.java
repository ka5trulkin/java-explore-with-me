package ru.practicum.main.exception.child;

import ru.practicum.main.exception.base.DataConflictException;

import static ru.practicum.utils.message.ExceptionMessage.EVENT_PARTICIPANT_LIMIT;

public class EventParticipantLimitException extends DataConflictException {
    public EventParticipantLimitException(Long eventId) {
        super(String.format(EVENT_PARTICIPANT_LIMIT, eventId));
    }
}