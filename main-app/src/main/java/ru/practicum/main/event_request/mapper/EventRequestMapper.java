package ru.practicum.main.event_request.mapper;

import com.querydsl.core.Tuple;
import lombok.experimental.UtilityClass;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.event_request.dto.EventRequestStatusUpdateResult;
import ru.practicum.main.event_request.dto.ParticipationRequestDto;
import ru.practicum.main.event_request.model.EventRequest;
import ru.practicum.main.event_request.model.Status;
import ru.practicum.main.exception.base.NotFoundException;
import ru.practicum.main.user.model.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.main.event.model.QEvent.event;
import static ru.practicum.main.event_request.model.Status.*;
import static ru.practicum.main.user.model.QUser.user;
import static ru.practicum.utils.message.ExceptionMessage.USER_OR_EVENT_IN_TUPLE_NOT_FOUND;

@UtilityClass
public class EventRequestMapper {
    private void checkUserAndEventExists(Tuple tuple) {
        if (tuple == null) {
            throw new NotFoundException(USER_OR_EVENT_IN_TUPLE_NOT_FOUND);
        }
    }

    public EventRequest toEventRequest(Tuple tuple) {
        checkUserAndEventExists(tuple);
        final User userResult = tuple.get(user);
        final Event eventResult = tuple.get(event);
        return EventRequest.builder()
                .event(eventResult)
                .requester(userResult)
                .status(eventResult.getRequestModeration() ? PENDING : CONFIRMED)
                .build();
    }

    public ParticipationRequestDto toRequestDto(EventRequest request) {
        return ParticipationRequestDto.builder()
                .id(request.getId())
                .created(request.getCreated())
                .event(request.getEvent().getId())
                .requester(request.getRequester().getId())
                .status(request.getStatus())
                .build();
    }

    public List<ParticipationRequestDto> toRequestDto(Collection<EventRequest> requests) {
        return requests.stream()
                .map(EventRequestMapper::toRequestDto)
                .collect(Collectors.toList());
    }

    public static EventRequestStatusUpdateResult updateRequestStatus(Collection<EventRequest> requests, Status status) {
        final List<ParticipationRequestDto> confirmedRequests = new ArrayList<>();
        final List<ParticipationRequestDto> rejectedRequests = new ArrayList<>();
        requests.forEach(request -> {
            final int confirmed = request.getEvent().getConfirmedRequests();
            final int participantLimit = request.getEvent().getParticipantLimit();
            final int unlimited = 0;
            if ((status == CONFIRMED) && ((participantLimit == unlimited) || (confirmed < participantLimit))) {
                request.setStatus(CONFIRMED);
                request.getEvent().setConfirmedRequests(confirmed + 1);
                confirmedRequests.add(EventRequestMapper.toRequestDto(request));
            } else {
                request.setStatus(REJECTED);
                rejectedRequests.add(EventRequestMapper.toRequestDto(request));
            }
        });
        return EventRequestStatusUpdateResult.builder()
                .confirmedRequests(confirmedRequests)
                .rejectedRequests(rejectedRequests)
                .build();
    }
}