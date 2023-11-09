package ru.practicum.main.event_comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.event_comment.dto.CommentDto;
import ru.practicum.main.event_comment.service.CommentService;
import ru.practicum.utils.PageApp;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static ru.practicum.main.event.model.EventSort.COMMENT_DATE;
import static ru.practicum.main.event.model.EventSort.sortBy;
import static ru.practicum.utils.Patterns.EVENT_COMMENT_PREFIX;
import static ru.practicum.utils.message.LogMessage.*;

@RestController
@Slf4j
@RequestMapping(path = EVENT_COMMENT_PREFIX)
@RequiredArgsConstructor
@Validated
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/users/{userId}")
    @ResponseStatus(CREATED)
    public CommentDto postComment(
            @PathVariable @Positive Long userId,
            @PathVariable @Positive Long eventId,
            @RequestBody @Valid CommentDto dto
    ) {
        log.info(REQUEST_ADD_COMMENT, userId, eventId);
        return commentService.postComment(userId, eventId, dto);
    }

    @PatchMapping("/{commentId}/users/{userId}")
    public CommentDto updateComment(
            @PathVariable @Positive Long userId,
            @PathVariable @Positive Long eventId,
            @PathVariable @Positive Long commentId,
            @RequestBody @Valid CommentDto dto
    ) {
        log.info(REQUEST_UPDATE_COMMENT, commentId, userId, eventId);
        return commentService.updateComment(userId, eventId, commentId, dto);
    }

    @GetMapping
    public List<CommentDto> getCommentList(
            @PathVariable @Positive Long eventId,
            @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
            @RequestParam(defaultValue = "10") @Positive Integer size
    ) {
        log.info(REQUEST_GET_COMMENT_LIST, eventId);
        return commentService.getEventList(eventId, PageApp.ofStartingIndex(from, size, sortBy(COMMENT_DATE)));
    }

    @DeleteMapping("/{commentId}/users/{userId}")
    @ResponseStatus(NO_CONTENT)
    public void deleteComment(
            @PathVariable @Positive Long userId,
            @PathVariable @Positive Long eventId,
            @PathVariable @Positive Long commentId
    ) {
        log.info(REQUEST_DELETE_COMMENT, commentId, userId, eventId);
        commentService.deleteComment(userId, eventId, commentId);
    }
}