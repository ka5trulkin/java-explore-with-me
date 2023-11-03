package ru.practicum.main.exception.child;

import ru.practicum.main.exception.base.NotFoundException;

import static ru.practicum.utils.message.ExceptionMessage.COMPILATION_NOT_FOUND;

public class CompilationNotFoundException extends NotFoundException {
    public CompilationNotFoundException(Long compId) {
        super(String.format(COMPILATION_NOT_FOUND, compId));
    }
}