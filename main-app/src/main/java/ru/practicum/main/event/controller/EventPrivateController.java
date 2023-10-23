package ru.practicum.main.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
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

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static ru.practicum.utils.Patterns.EVENT_PRIVATE_PREFIX;
import static ru.practicum.utils.message.LogMessage.*;

@RestController
@Slf4j
@RequestMapping(path = EVENT_PRIVATE_PREFIX)
@RequiredArgsConstructor
public class EventPrivateController {
    private final EventService eventService;

    @PostMapping
    @ResponseStatus(CREATED)
    public EventFullDto postEvent(@PathVariable Long userId, @Validated(CreateInfo.class) @RequestBody EventCreateDto dto) {
        log.info(REQUEST_ADD_EVENT, dto.getTitle());
        return eventService.postEvent(userId, dto);
    }

    @GetMapping("/{eventId}")
    public EventFullDto getEvent(@PathVariable Long userId, @PathVariable Long eventId) {
        log.info(REQUEST_GET_EVENT, eventId);
        return eventService.getEvent(userId, eventId);
    }

    @GetMapping
    public List<EventFullDto> getEventList(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
            @RequestParam(defaultValue = "10") @Positive Integer size
    ) {
        log.info(REQUEST_GET_EVENT_LIST_BY_INITIATOR, userId);
        return eventService.getEventList(userId, PageApp.ofStartingIndex(from, size));
    }

    @PatchMapping("/{eventId}")
    public EventFullDto updateEvent(
            @PathVariable Long userId,
            @PathVariable Long eventId,
            @RequestBody @Validated(UpdateInfo.class) UpdateEventRequest dto
    ) {
        log.info(REQUEST_UPDATE_EVENT, eventId);
        return eventService.updateEvent(userId, eventId, dto);
    }

    @GetMapping("/{eventId}/requests")
    public List<ParticipationRequestDto> getRequestList(@PathVariable Long userId, @PathVariable Long eventId) {
        log.info(REQUEST_GET_EVENT_REQUEST_LIST, userId);
        return eventService.getRequestList(userId, eventId);
    }

    @PatchMapping("/{eventId}/requests")
    public EventRequestStatusUpdateResult updateRequestStatus(
            @PathVariable Long userId,
            @PathVariable Long eventId,
            @RequestBody EventRequestStatusUpdateRequest dto
    ) {
        log.info(REQUEST_UPDATE_EVENT_REQUEST_STATUS, eventId);
        return eventService.updateRequestStatus(userId, eventId, dto);
    }
}