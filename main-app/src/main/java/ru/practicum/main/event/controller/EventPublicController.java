package ru.practicum.main.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.event.dto.EventFilter;
import ru.practicum.main.event.dto.EventFullDto;
import ru.practicum.main.event.model.EventSort;
import ru.practicum.main.event.service.EventService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.utils.Patterns.DATE_TIME;
import static ru.practicum.utils.Patterns.EVENT_PUBLIC_PREFIX;
import static ru.practicum.utils.message.LogMessage.REQUEST_GET_EVENT;
import static ru.practicum.utils.message.LogMessage.REQUEST_GET_EVENT_LIST;

@RestController
@Slf4j
@RequestMapping(path = EVENT_PUBLIC_PREFIX)
@RequiredArgsConstructor
@Validated
public class EventPublicController {
    private final EventService eventService;

    @GetMapping
    public List<EventFullDto> getEventList(
            @RequestParam(required = false) String text,
            @RequestParam(required = false) List<Long> categories,
            @RequestParam(required = false) Boolean paid,
            @RequestParam(required = false) @DateTimeFormat(pattern = DATE_TIME) LocalDateTime rangeStart,
            @RequestParam(required = false) @DateTimeFormat(pattern = DATE_TIME) LocalDateTime rangeEnd,
            @RequestParam(defaultValue = "false") boolean onlyAvailable,
            @RequestParam(required = false) EventSort sort,
            @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
            @RequestParam(defaultValue = "10") @Positive Integer size,
            HttpServletRequest request
    ) {
        log.info(REQUEST_GET_EVENT_LIST);
        return eventService.getEventList(
                EventFilter.of(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size),
                request
        );
    }
//
    @GetMapping("/{eventId}")
    public EventFullDto getEvent(@PathVariable @Positive Long eventId, HttpServletRequest request) {
        log.info(REQUEST_GET_EVENT, eventId);
        return eventService.getEvent(eventId, request);
    }
}