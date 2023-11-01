package ru.practicum.main.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.event.dto.EventFilter;
import ru.practicum.main.event.dto.EventFullDto;
import ru.practicum.main.event.model.State;
import ru.practicum.main.event.service.EventService;
import ru.practicum.main.event_request.dto.UpdateEventRequest;
import ru.practicum.utils.marker.AdminInfo;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.utils.Patterns.DATE_TIME;
import static ru.practicum.utils.Patterns.EVENT_ADMIN_PREFIX;
import static ru.practicum.utils.message.LogMessage.REQUEST_GET_EVENT_LIST;
import static ru.practicum.utils.message.LogMessage.REQUEST_UPDATE_EVENT;

@RestController
@Slf4j
@RequestMapping(path = EVENT_ADMIN_PREFIX)
@RequiredArgsConstructor
@Validated
public class EventAdminController {
    private final EventService eventService;

    @GetMapping
    public List<EventFullDto> getEventList(
            @RequestParam(required = false) List<Long> users,
            @RequestParam(required = false) List<State> states,
            @RequestParam(required = false) List<Long> categories,
            @RequestParam(required = false) @DateTimeFormat(pattern = DATE_TIME) LocalDateTime rangeStart,
            @RequestParam(required = false) @DateTimeFormat(pattern = DATE_TIME) LocalDateTime rangeEnd,
            @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
            @RequestParam(defaultValue = "10") @Positive Integer size
    ) {
        log.info(REQUEST_GET_EVENT_LIST);
        return eventService.getEventList(EventFilter.of(users, states, categories, rangeStart, rangeEnd, from, size));
    }

    @PatchMapping("/{eventId}")
    public EventFullDto updateEvent(
            @PathVariable @Positive Long eventId,
            @RequestBody @Validated(AdminInfo.class) UpdateEventRequest dto
    ) {
        log.info(REQUEST_UPDATE_EVENT, eventId);
        return eventService.updateEvent(eventId, dto);
    }
}