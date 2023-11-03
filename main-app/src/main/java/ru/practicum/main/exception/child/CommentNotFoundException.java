package ru.practicum.main.exception.child;

import ru.practicum.main.exception.base.NotFoundException;

import static ru.practicum.utils.message.ExceptionMessage.COMMENT_NOT_FOUND;

public class CommentNotFoundException extends NotFoundException {
    public CommentNotFoundException(Long commentId) {
        super(String.format(COMMENT_NOT_FOUND, commentId));
    }
}