package ru.practicum.main.exception.child;

import ru.practicum.main.exception.base.DataConflictException;

import static ru.practicum.utils.message.ExceptionMessage.CATEGORY_NAME_ALREADY_EXIST;

public class CategoryNameAlreadyExistException extends DataConflictException {
    public CategoryNameAlreadyExistException(String email) {
        super(String.format(CATEGORY_NAME_ALREADY_EXIST, email));
    }
}