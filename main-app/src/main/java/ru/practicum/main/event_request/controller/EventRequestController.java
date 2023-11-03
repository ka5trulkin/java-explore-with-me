package ru.practicum.main.event_request.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.event_request.dto.ParticipationRequestDto;
import ru.practicum.main.event_request.service.EventRequestService;

import javax.validation.constraints.Positive;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static ru.practicum.utils.Patterns.EVENT_REQUEST_PREFIX;
import static ru.practicum.utils.message.LogMessage.*;

@RestController
@Slf4j
@RequestMapping(path = EVENT_REQUEST_PREFIX)
@RequiredArgsConstructor
@Validated
public class EventRequestController {
    private final EventRequestService requestService;

    @PostMapping
    @ResponseStatus(CREATED)
    public ParticipationRequestDto postEventRequest(
            @PathVariable @Positive Long userId,
            @RequestParam @Positive Long eventId
    ) {
        log.info(REQUEST_ADD_EVENT_REQUEST, eventId);
        return requestService.postEventRequest(userId, eventId);
    }

    @GetMapping
    public List<ParticipationRequestDto> getEventRequestList(@PathVariable @Positive Long userId) {
        log.info(REQUEST_GET_EVENT_REQUEST_LIST, userId);
        return requestService.getEventRequestList(userId);
    }

    @PatchMapping("/{requestId}/cancel")
    public ParticipationRequestDto cancelEventRequest(
            @PathVariable @Positive Long userId,
            @PathVariable @Positive Long requestId
    ) {
        log.info(REQUEST_CANCEL_EVENT_REQUEST, requestId);
        return requestService.cancelEventRequest(userId, requestId);
    }
}