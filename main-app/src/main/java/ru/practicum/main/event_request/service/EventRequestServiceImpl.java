package ru.practicum.main.event_request.service;

import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.event_request.dto.ParticipationRequestDto;
import ru.practicum.main.event_request.mapper.EventRequestMapper;
import ru.practicum.main.event_request.model.EventRequest;
import ru.practicum.main.event_request.storage.EventRequestRepository;
import ru.practicum.main.exception.child.*;

import javax.persistence.EntityManager;
import java.util.List;

import static ru.practicum.main.event.model.State.PUBLISHED;
import static ru.practicum.main.event_request.model.QEventRequest.eventRequest;
import static ru.practicum.main.event_request.model.Status.CANCELED;
import static ru.practicum.main.event_request.model.Status.CONFIRMED;
import static ru.practicum.utils.message.LogMessage.*;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EventRequestServiceImpl implements EventRequestService {
    private final EntityManager manager;
    private final EventRequestRepository requestRepo;

    private void checkRequestExists(Long userId, Long eventId) {
        final boolean isExists = requestRepo.exists(eventRequest.requester.id.eq(userId).and(eventRequest.event.id.eq(eventId)));
        if (isExists) {
            throw new EventRequestAlreadyExistException(eventId);
        }
    }

    private void checkIsValidRequest(EventRequest request) {
        final Long requesterId = request.getRequester().getId();
        final Long initiatorId = request.getEvent().getInitiator().getId();
        final Event event = request.getEvent();
        final Long eventId = event.getId();
        final int unlimited = 0;
        if (requesterId.equals(initiatorId)) {
            throw new EventRequesterIsInitiatorException(eventId);
        }
        if (event.getState() != PUBLISHED) {
            throw new EventNotPublishedException(eventId);
        }
        if ((event.getParticipantLimit() > unlimited) && (event.getConfirmedRequests().equals(event.getParticipantLimit()))) {
            throw new EventParticipantLimitException(eventId);
        }
        if ((!event.getRequestModeration()) || (event.getParticipantLimit() == unlimited)) {
            request.setStatus(CONFIRMED);
            this.incrementConfirmedRequests(request);
        }
    }

    private void incrementConfirmedRequests(EventRequest request) {
            final int numberOfConfirmedRequests = request.getEvent().getConfirmedRequests();
            request.getEvent().setConfirmedRequests(numberOfConfirmedRequests + 1);
    }

    @Override
    @Transactional
    public ParticipationRequestDto postEventRequest(Long userId, Long eventId) {
        this.checkRequestExists(userId, eventId);
        final Tuple userAndEvent = requestRepo.getUserAndEvent(userId, eventId, manager);
        final EventRequest request = EventRequestMapper.toEventRequest(userAndEvent);
        this.checkIsValidRequest(request);
        log.info(EVENT_REQUEST_ADDED, request.getEvent().getId());
        return EventRequestMapper.toRequestDto(requestRepo.save(request));
    }

    @Override
    public List<ParticipationRequestDto> getEventRequestList(Long userId) {
        final List<EventRequest> requests = requestRepo.findAll(eventRequest.requester.id.eq(userId));
        log.info(GET_EVENT_REQUEST_LIST, userId);
        return EventRequestMapper.toRequestDto(requests);
    }

    @Override
    @Transactional
    public ParticipationRequestDto cancelEventRequest(Long userId, Long requestId) {
        final EventRequest request = requestRepo.findOne(eventRequest.requester.id.eq(userId).and(eventRequest.id.eq(requestId)))
                .orElseThrow(() -> new EventRequestNotFoundException(requestId));
        request.setStatus(CANCELED);
        log.info(EVENT_REQUEST_CANCELED, requestId);
        return EventRequestMapper.toRequestDto(request);
    }
}