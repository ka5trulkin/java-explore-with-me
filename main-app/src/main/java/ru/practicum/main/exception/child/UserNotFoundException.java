package ru.practicum.main.exception.child;

import ru.practicum.main.exception.base.NotFoundException;

import static ru.practicum.utils.message.ExceptionMessage.USER_NOT_FOUND;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException(long id) {
        super(String.format(USER_NOT_FOUND, id));
    }
}