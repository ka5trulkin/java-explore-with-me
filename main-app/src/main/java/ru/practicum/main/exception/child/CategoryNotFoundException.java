package ru.practicum.main.exception.child;

import ru.practicum.main.exception.base.NotFoundException;

import static ru.practicum.utils.message.ExceptionMessage.CATEGORY_NOT_FOUND;

public class CategoryNotFoundException extends NotFoundException {
    public CategoryNotFoundException(long id) {
        super(String.format(CATEGORY_NOT_FOUND, id));
    }
}