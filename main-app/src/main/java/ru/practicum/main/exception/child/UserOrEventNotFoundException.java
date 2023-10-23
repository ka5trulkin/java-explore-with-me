package ru.practicum.main.exception.child;

import ru.practicum.main.exception.base.NotFoundException;

import static ru.practicum.utils.message.ExceptionMessage.USER_OR_EVENT_NOT_FOUND;

public class UserOrEventNotFoundException extends NotFoundException {
    public UserOrEventNotFoundException(long userId, long eventId) {
        super(String.format(USER_OR_EVENT_NOT_FOUND, userId, eventId));
    }
}