package ru.practicum.main.event_request.service;

import ru.practicum.main.event_request.dto.ParticipationRequestDto;

import java.util.List;

public interface EventRequestService {
    ParticipationRequestDto postEventRequest(Long userId, Long eventId);

    List<ParticipationRequestDto> getEventRequestList(Long userId);

    ParticipationRequestDto cancelEventRequest(Long userId, Long requestId);
}