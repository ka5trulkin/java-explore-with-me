package ru.practicum.main.event.service;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.category.storage.CategoryRepository;
import ru.practicum.main.event.dto.CommentDto;
import ru.practicum.main.event.dto.EventCreateDto;
import ru.practicum.main.event.dto.EventFilter;
import ru.practicum.main.event.dto.EventFullDto;
import ru.practicum.main.event.mapper.EventMapper;
import ru.practicum.main.event.model.Comment;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.event.model.State;
import ru.practicum.main.event.storage.CommentRepository;
import ru.practicum.main.event.storage.EventRepository;
import ru.practicum.main.event.storage.LocationRepository;
import ru.practicum.main.event_request.dto.EventRequestStatusUpdateRequest;
import ru.practicum.main.event_request.dto.EventRequestStatusUpdateResult;
import ru.practicum.main.event_request.dto.ParticipationRequestDto;
import ru.practicum.main.event_request.dto.UpdateEventRequest;
import ru.practicum.main.event_request.mapper.EventRequestMapper;
import ru.practicum.main.event_request.model.EventRequest;
import ru.practicum.main.event_request.model.Status;
import ru.practicum.main.event_request.storage.EventRequestRepository;
import ru.practicum.main.exception.child.CommentNotFoundException;
import ru.practicum.main.exception.child.EventNotFoundException;
import ru.practicum.main.exception.child.EventUpdateStatusRequestException;
import ru.practicum.main.exception.child.UserOrEventNotFoundException;
import ru.practicum.stats.client.StatsClient;
import ru.practicum.stats.dto.StatsHitCreate;
import ru.practicum.stats.dto.StatsHitResponse;
import ru.practicum.utils.QPredicate;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static java.time.LocalDateTime.now;
import static ru.practicum.main.event.model.QComment.comment;
import static ru.practicum.main.event.model.QEvent.event;
import static ru.practicum.main.event.model.State.PUBLISHED;
import static ru.practicum.main.event_request.model.QEventRequest.eventRequest;
import static ru.practicum.main.user.model.QUser.user;
import static ru.practicum.utils.Patterns.EVENT_PATH;
import static ru.practicum.utils.Patterns.MAIN_APP;
import static ru.practicum.utils.message.LogMessage.*;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EntityManager manager;
    private final EventRepository eventRepo;
    private final LocationRepository locationRepo;
    private final CategoryRepository categoryRepo;
    private final EventRequestRepository requestRepo;
    private final CommentRepository commentRepo;
    private final StatsClient statsClient;
    private final int marginForRequestDelays = 1;

    private void checkRequestOnUpdate(Long userId, Long eventId, List<Long> requestIds) {
        final boolean isExists = eventRepo.exists(event.id.eq(eventId));
        if (!isExists) {
            throw new EventNotFoundException(eventId);
        }
        final boolean isValid = eventRepo.exists(event.id.eq(eventId)
                .and(event.initiator.id.eq(userId))
                .and(event.participantLimit.gt(0))
                .and(event.participantLimit.ne(event.confirmedRequests))
                .and(event.requestModeration.ne(false)));
        if (!isValid) {
            throw new EventUpdateStatusRequestException(eventId);
        }
        final boolean isPending = requestRepo.exists(eventRequest.id.in(requestIds).and(eventRequest.status.eq(Status.PENDING)));
        if (!isPending) {
            throw new EventUpdateStatusRequestException(eventId);
        }
    }

    private void checkCommentExists(Predicate predicate, Long commentId) {
        if (!commentRepo.exists(predicate)) {
            throw new CommentNotFoundException(commentId);
        }
    }

    private void setEventViews(Event event, HttpServletRequest request) {
        final String eventUri = this.getUri(event);
        final List<StatsHitResponse> stats
                = statsClient.getStats(event.getCreatedOn().minusHours(marginForRequestDelays), now(), List.of(eventUri), true);
        stats.forEach(
                stat -> {
                    if (stat.getUri().equals(eventUri)) {
                        event.setViews((int) stat.getHits());
                    }
                }
        );
        this.pushStatsHit(request, eventUri);
    }

    private void setEventViews(List<Event> events, EventFilter filter, HttpServletRequest request) {
        final Map<String, Event> eventMap = events.stream()
                .collect(Collectors.toMap(this::getUri, event -> event));
        final LocalDateTime statsStart = filter.getRangeStart() == null ? this.getEarliestTime(events) : filter.getRangeStart();
        final LocalDateTime statsEnd = filter.getRangeEnd() == null ? now() : filter.getRangeEnd();
        final ArrayList<String> urisToStats = new ArrayList<>(eventMap.keySet());
        final List<StatsHitResponse> stats = statsClient.getStats(statsStart, statsEnd, urisToStats, true);
        stats.forEach(
                stat -> {
                    final String key = stat.getUri();
                    if (eventMap.containsKey(key)) {
                        eventMap.get(key).setViews((int) stat.getHits());
                    }
                }
        );
        eventMap.keySet().forEach(uri -> this.pushStatsHit(request, uri));
    }

    private Event getEventOrThrow(Long userId, Long eventId) {
        return eventRepo.findOne(event.id.eq(eventId).and(event.initiator.id.eq(userId)))
                .orElseThrow(() -> new UserOrEventNotFoundException(userId, eventId));
    }

    private void pushStatsHit(HttpServletRequest request, String uri) {
        final StatsHitCreate stats = StatsHitCreate.builder()
                .app(MAIN_APP)
                .uri(uri)
                .ip(request.getRemoteAddr())
                .timestamp(now())
                .build();
        statsClient.postHit(stats);
    }

    private LocalDateTime getEarliestTime(List<Event> events) {
        if (!events.isEmpty()) {
            return events.stream()
                    .min(Comparator.comparing(Event::getCreatedOn))
                    .get()
                    .getCreatedOn().minusHours(marginForRequestDelays);
        } else return now();
    }

    private String getUri(Event event) {
        return String.format(EVENT_PATH, event.getId());
    }

    private Predicate getCommentPredicate(Long eventId, Long commentId) {
        return QPredicate.builder()
                .add(comment.id.eq(commentId))
                .add(comment.event.id.eq(eventId))
                .buildAnd();
    }

    private Predicate getCommentPredicate(Long userId, Long eventId, Long commentId) {
        return QPredicate.builder()
                .add(this.getCommentPredicate(eventId, commentId))
                .add(comment.commentator.id.eq(userId))
                .buildAnd();
    }

    private Predicate getEventPredicate(EventFilter filter) {
        return QPredicate.builder()
                .add(filter.getCategories(), event.category.id::in)
                .add(filter.getRangeStart(), event.createdOn::after)
                .add(filter.getRangeEnd(), event.createdOn::before)
                .buildAnd();
    }

    private Predicate getPredicateAdmin(EventFilter filter) {
        return QPredicate.builder()
                .add(this.getEventPredicate(filter))
                .add(filter.getUsers(), event.initiator.id::in)
                .add(filter.getStates(), event.state::in)
                .buildAnd();
    }

    private Predicate getPredicatePublic(EventFilter filter) {
        Predicate predicateForText = null;
        Predicate predicateForAvailable = null;
        if (filter.getText() != null) {
            predicateForText = event.annotation.containsIgnoreCase(filter.getText())
                    .or(event.description.containsIgnoreCase(filter.getText()));
        }
        if (filter.getOnlyAvailable()) {
            predicateForAvailable = event.confirmedRequests.lt(event.participantLimit);
        }
        return QPredicate.builder()
                .add(this.getEventPredicate(filter))
                .add(predicateForText)
                .add(predicateForAvailable)
                .add(filter.getRangeStart(), event.createdOn::after)
                .add(filter.getPaid(), event.paid::eq)
                .buildAnd();
    }

    private List<Comment> getComments(Collection<Event> events, PageRequest page) {
        final List<Long> eventIds = events.stream()
                .map(Event::getId)
                .collect(Collectors.toList());
        return commentRepo.findAll(comment.event.id.in(eventIds), page).toList();
    }

    @Override
    @Transactional
    public EventFullDto postEvent(Long userId, EventCreateDto dto) {
        final Tuple userAndCategory = eventRepo.getUserAndCategory(userId, dto.getCategory(), manager);
        Event event = EventMapper.toEvent(dto, userAndCategory);
        locationRepo.save(event.getLocation());
        log.info(EVENT_ADDED, event.getId(), event.getTitle());
        return EventMapper.toEventFullDto(eventRepo.save(event));
    }

    @Override
    public EventFullDto getEvent(Long userId, Long eventId) {
        final Event event = this.getEventOrThrow(userId, eventId);
        log.info(GET_EVENT, eventId);
        return EventMapper.toEventFullDto(event);
    }

    @Override
    @Transactional
    public EventFullDto getEvent(Long eventId, HttpServletRequest request) {
        final Event entity = eventRepo.findOne(event.id.in(eventId).and(event.state.eq(PUBLISHED)))
                .orElseThrow(() -> new EventNotFoundException(eventId));
        this.setEventViews(entity, request);
        log.info(GET_EVENT, eventId);
        return EventMapper.toEventFullDto(entity);
    }

    @Override
    public List<EventFullDto> getEventList(Long userId, PageRequest eventPage, PageRequest commentPage) {
        final Predicate predicate = event.initiator.id.eq(userId);
        final List<Event> events = eventRepo.findAll(predicate, eventPage).toList();
        final List<Comment> comments = this.getComments(events, commentPage);
        log.info(GET_EVENT_LIST_BY_INITIATOR, userId);
        return EventMapper.toEventFullDto(events, comments);
    }

    @Override
    public List<EventFullDto> getEventList(EventFilter filter) {
        final Predicate predicate = this.getPredicateAdmin(filter);
        final List<Event> events = predicate == null
                ? eventRepo.findAll(filter.getEventPage()).toList()
                : eventRepo.findAll(predicate, filter.getEventPage()).toList();
        final List<Comment> comments = this.getComments(events, filter.getCommentPage());
        log.info(GET_EVENT_LIST);
        return EventMapper.toEventFullDto(events, comments);
    }

    @Override
    @Transactional
    public List<EventFullDto> getEventList(EventFilter filter, HttpServletRequest request) {
        final Predicate predicate = this.getPredicatePublic(filter);
        final List<Event> events = predicate == null
                ? eventRepo.findAll(filter.getEventPage()).toList()
                : eventRepo.findAll(predicate, filter.getEventPage()).toList();
        this.setEventViews(events, filter, request);
        log.info(GET_EVENT_LIST);
        final List<Comment> comments = this.getComments(events, filter.getCommentPage());
        return EventMapper.toEventFullDto(events, comments);
    }

    @Override
    @Transactional
    public EventFullDto updateEvent(Long userId, Long eventId, UpdateEventRequest dto) {
        final Event event = this.getEventOrThrow(userId, eventId);
        EventMapper.updateEventByUser(event, dto, categoryRepo);
        log.info(EVENT_UPDATED, eventId);
        return EventMapper.toEventFullDto(event);
    }

    @Override
    @Transactional
    public EventFullDto updateEvent(Long eventId, UpdateEventRequest dto) {
        final Event event = eventRepo.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));
        EventMapper.updateEventByAdmin(event, dto, categoryRepo);
        log.info(EVENT_UPDATED, eventId);
        return EventMapper.toEventFullDto(event);
    }

    @Override
    public List<ParticipationRequestDto> getRequestList(Long userId, Long eventId) {
        final List<EventRequest> requests = requestRepo.findAll(eventRequest.event.initiator.id.eq(userId)
                .and(eventRequest.event.id.eq(eventId)));
        log.info(GET_EVENT_REQUEST_LIST, userId);
        return EventRequestMapper.toRequestDto(requests);
    }

    @Override
    @Transactional
    public EventRequestStatusUpdateResult updateRequestStatus(Long userId, Long eventId, EventRequestStatusUpdateRequest dto) {
        this.checkRequestOnUpdate(userId, eventId, dto.getRequestIds());
        final List<EventRequest> requests = requestRepo.findAll(eventRequest.id.in(dto.getRequestIds()));
        log.info(EVENT_REQUEST_STATUS_UPDATED, eventId);
        return EventRequestMapper.updateRequestStatus(requests, dto.getStatus());
    }

    @Override
    @Transactional
    public CommentDto postComment(Long userId, Long eventId, CommentDto dto) {
        final Predicate predicate = QPredicate.builder()
                .add(user.id.eq(userId))
                .add(event.id.eq(eventId))
                .add(event.state.ne(State.PENDING))
                .buildAnd();
        final Tuple userAndEvent = eventRepo.getUserAndEvent(predicate, manager);
        final Comment comment = commentRepo.save(EventMapper.toComment(userAndEvent, dto));
        comment.getEvent().addComment(comment);
        log.info(COMMENT_ADDED, userId, eventId);
        return EventMapper.toCommentDto(comment);
    }

    @Override
    @Transactional
    public CommentDto updateComment(Long userId, Long eventId, Long commentId, CommentDto dto) {
        final Predicate predicate = this.getCommentPredicate(userId, eventId, commentId);
        final Comment entity = commentRepo.findOne(predicate)
                .orElseThrow(() -> new CommentNotFoundException(commentId));
        EventMapper.updateComment(entity, dto);
        log.info(COMMENT_UPDATED, commentId, userId, eventId);
        return EventMapper.toCommentDto(entity);
    }

    @Override
    @Transactional
    public void deleteComment(Long userId, Long eventId, Long commentId) {
        final Predicate predicate = this.getCommentPredicate(userId, eventId, commentId);
        this.checkCommentExists(predicate, commentId);
        commentRepo.deleteById(commentId);
        log.info(COMMENT_DELETED, commentId, userId, eventId);
    }

    @Override
    @Transactional
    public void deleteComment(Long eventId, Long commentId) {
        final Predicate predicate = this.getCommentPredicate(eventId, commentId);
        this.checkCommentExists(predicate, commentId);
        commentRepo.deleteById(commentId);
        log.info(COMMENT_DELETED_ADMIN, commentId, eventId);
    }
}