package ru.practicum.main.event.service;

import org.springframework.data.domain.PageRequest;
import ru.practicum.main.event.dto.EventCreateDto;
import ru.practicum.main.event.dto.EventFilter;
import ru.practicum.main.event.dto.EventFullDto;
import ru.practicum.main.event_request.dto.EventRequestStatusUpdateRequest;
import ru.practicum.main.event_request.dto.EventRequestStatusUpdateResult;
import ru.practicum.main.event_request.dto.ParticipationRequestDto;
import ru.practicum.main.event_request.dto.UpdateEventRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface EventService {
    EventFullDto postEvent(Long userId, EventCreateDto dto);

    EventFullDto getEvent(Long userId, Long eventId);

    EventFullDto getEvent(Long eventId, HttpServletRequest request);

    List<EventFullDto> getEventList(Long userId, PageRequest eventPage);

    List<EventFullDto> getEventList(EventFilter filter);

    List<EventFullDto> getEventList(EventFilter filter, HttpServletRequest request);

    EventFullDto updateEvent(Long userId, Long eventId, UpdateEventRequest dto);

    EventFullDto updateEvent(Long eventId, UpdateEventRequest dto);

    List<ParticipationRequestDto> getRequestList(Long userId, Long eventId);

    EventRequestStatusUpdateResult updateRequestStatus(Long userId, Long eventId, EventRequestStatusUpdateRequest dto);
}