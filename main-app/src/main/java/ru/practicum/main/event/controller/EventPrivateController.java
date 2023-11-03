package ru.practicum.main.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.event.dto.CommentDto;
import ru.practicum.main.event.dto.EventCreateDto;
import ru.practicum.main.event.dto.EventFullDto;
import ru.practicum.main.event.service.EventService;
import ru.practicum.main.event_request.dto.EventRequestStatusUpdateRequest;
import ru.practicum.main.event_request.dto.EventRequestStatusUpdateResult;
import ru.practicum.main.event_request.dto.ParticipationRequestDto;
import ru.practicum.main.event_request.dto.UpdateEventRequest;
import ru.practicum.utils.PageApp;
import ru.practicum.utils.marker.CreateInfo;
import ru.practicum.utils.marker.UpdateInfo;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static ru.practicum.main.event.model.EventSort.COMMENT_DATE;
import static ru.practicum.main.event.model.EventSort.sortBy;
import static ru.practicum.utils.Patterns.EVENT_PRIVATE_PREFIX;
import static ru.practicum.utils.message.LogMessage.*;
import static ru.practicum.utils.message.LogMessage.REQUEST_DELETE_COMMENT;

@RestController
@Slf4j
@RequestMapping(path = EVENT_PRIVATE_PREFIX)
@RequiredArgsConstructor
@Validated
public class EventPrivateController {
    private final EventService eventService;

    @PostMapping
    @ResponseStatus(CREATED)
    public EventFullDto postEvent(
            @PathVariable @Positive Long userId,
            @Validated(CreateInfo.class) @RequestBody EventCreateDto dto
    ) {
        log.info(REQUEST_ADD_EVENT, dto.getTitle());
        return eventService.postEvent(userId, dto);
    }

    @GetMapping("/{eventId}")
    public EventFullDto getEvent(@PathVariable @Positive Long userId, @PathVariable @Positive Long eventId) {
        log.info(REQUEST_GET_EVENT, eventId);
        return eventService.getEvent(userId, eventId);
    }

    @GetMapping
    public List<EventFullDto> getEventList(
            @PathVariable @Positive Long userId,
            @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
            @RequestParam(defaultValue = "10") @Positive Integer size,
            @RequestParam(defaultValue = "0") @PositiveOrZero Integer commentFrom,
            @RequestParam(defaultValue = "10") @Positive Integer commentSize
    ) {
        log.info(REQUEST_GET_EVENT_LIST_BY_INITIATOR, userId);
        return eventService.getEventList(
                userId,
                PageApp.ofStartingIndex(from, size),
                PageApp.ofStartingIndex(commentFrom, commentSize, sortBy(COMMENT_DATE))
        );
    }

    @PatchMapping("/{eventId}")
    public EventFullDto updateEvent(
            @PathVariable @Positive Long userId,
            @PathVariable @Positive Long eventId,
            @RequestBody @Validated(UpdateInfo.class) UpdateEventRequest dto
    ) {
        log.info(REQUEST_UPDATE_EVENT, eventId);
        return eventService.updateEvent(userId, eventId, dto);
    }

    @GetMapping("/{eventId}/requests")
    public List<ParticipationRequestDto> getRequestList(
            @PathVariable @Positive Long userId,
            @PathVariable @Positive Long eventId
    ) {
        log.info(REQUEST_GET_EVENT_REQUEST_LIST, userId);
        return eventService.getRequestList(userId, eventId);
    }

    @PatchMapping("/{eventId}/requests")
    public EventRequestStatusUpdateResult updateRequestStatus(
            @PathVariable @Positive Long userId,
            @PathVariable @Positive Long eventId,
            @RequestBody EventRequestStatusUpdateRequest dto
    ) {
        log.info(REQUEST_UPDATE_EVENT_REQUEST_STATUS, eventId);
        return eventService.updateRequestStatus(userId, eventId, dto);
    }

    @PostMapping("/{eventId}/comments")
    @ResponseStatus(CREATED)
    public CommentDto postComment(
            @PathVariable @Positive Long userId,
            @PathVariable @Positive Long eventId,
            @RequestBody @Valid CommentDto dto
    ) {
        log.info(REQUEST_ADD_COMMENT, userId, eventId);
        return eventService.postComment(userId, eventId, dto);
    }

    @PatchMapping("/{eventId}/comments/{commentId}")
    public CommentDto updateComment(
            @PathVariable @Positive Long userId,
            @PathVariable @Positive Long eventId,
            @PathVariable @Positive Long commentId,
            @RequestBody @Valid CommentDto dto
    ) {
        log.info(REQUEST_UPDATE_COMMENT, commentId, userId, eventId);
        return eventService.updateComment(userId, eventId, commentId, dto);
    }

    @DeleteMapping("/{eventId}/comments/{commentId}")
    @ResponseStatus(NO_CONTENT)
    public void deleteComment(
            @PathVariable @Positive Long userId,
            @PathVariable @Positive Long eventId,
            @PathVariable @Positive Long commentId
    ) {
        log.info(REQUEST_DELETE_COMMENT, commentId, userId, eventId);
        eventService.deleteComment(userId, eventId, commentId);
    }
}