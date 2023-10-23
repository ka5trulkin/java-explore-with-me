package ru.practicum.main.exception.child;

import ru.practicum.main.exception.base.DataConflictException;

import static ru.practicum.utils.message.ExceptionMessage.USER_EMAIL_ALREADY_EXIST;

public class UserEmailAlreadyExistException extends DataConflictException {
    public UserEmailAlreadyExistException(String email) {
        super(String.format(USER_EMAIL_ALREADY_EXIST, email));
    }
}