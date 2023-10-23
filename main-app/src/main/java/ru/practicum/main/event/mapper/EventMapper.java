package ru.practicum.main.event.mapper;

import com.querydsl.core.Tuple;
import lombok.experimental.UtilityClass;
import ru.practicum.main.category.mapper.CategoryMapper;
import ru.practicum.main.category.mogel.Category;
import ru.practicum.main.category.storage.CategoryRepository;
import ru.practicum.main.event.dto.EventCreateDto;
import ru.practicum.main.event.dto.EventFullDto;
import ru.practicum.main.event.dto.EventShortDto;
import ru.practicum.main.event.dto.LocationDto;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.event.model.Location;
import ru.practicum.main.event_request.dto.UpdateEventRequest;
import ru.practicum.main.exception.base.NotFoundException;
import ru.practicum.main.exception.child.CategoryNotFoundException;
import ru.practicum.main.exception.child.EventCancelingException;
import ru.practicum.main.exception.child.EventPublishingException;
import ru.practicum.main.user.mapper.UserMapper;
import ru.practicum.main.user.model.User;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.LocalDateTime.now;
import static java.util.Objects.nonNull;
import static ru.practicum.main.category.mogel.QCategory.category;
import static ru.practicum.main.event.model.State.*;
import static ru.practicum.main.user.model.QUser.user;
import static ru.practicum.utils.message.ExceptionMessage.USER_OR_CATEGORY_IN_TUPLE_NOT_FOUND;

@UtilityClass
public class EventMapper {
    private void checkUserAndContentExists(Tuple tuple) {
        if (tuple == null) {
            throw new NotFoundException(USER_OR_CATEGORY_IN_TUPLE_NOT_FOUND);
        }
    }

    private boolean notBlank(String string) {
        return string != null && !string.isBlank();
    }

    public Event toEvent(EventCreateDto dto, Tuple tuple) {
        checkUserAndContentExists(tuple);
        final User initiator = tuple.get(user);
        final Category categoryEvent = tuple.get(category);
        final Location location = toLocation(dto.getLocation());
        return Event.builder()
                .category(categoryEvent)
                .annotation(dto.getAnnotation())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .eventDate(dto.getEventDate())
                .location(location)
                .initiator(initiator)
                .requestModeration(dto.getRequestModeration() == null || dto.getRequestModeration())
                .confirmedRequests(0)
                .participantLimit(dto.getParticipantLimit() == null ? 0 : dto.getParticipantLimit())
                .paid(dto.getPaid() != null && dto.getPaid())
                .state(PENDING)
                .views(0)
                .build();
    }

    public EventFullDto toEventFullDto(Event event) {
        return EventFullDto.builder()
                .id(event.getId())
                .description(event.getDescription())
                .annotation(event.getAnnotation())
                .category(CategoryMapper.toCategoryDto(event.getCategory()))
                .confirmedRequests(event.getConfirmedRequests())
                .eventDate(event.getEventDate())
                .initiator(UserMapper.toUserShortDto(event.getInitiator()))
                .paid(event.getPaid())
                .title(event.getTitle())
                .views(event.getViews())
                .location(toLocationDto(event.getLocation()))
                .requestModeration(event.getRequestModeration())
                .participantLimit(event.getParticipantLimit())
                .createdOn(event.getCreatedOn())
                .publishedOn(event.getPublishedOn())
                .state(event.getState())
                .build();
    }

    public List<EventFullDto> toEventFullDto(Collection<Event> events) {
        return events.stream()
                .map(EventMapper::toEventFullDto)
                .collect(Collectors.toList());
    }

    public Location toLocation(LocationDto dto) {
        return Location.builder()
                .lat(dto.getLat())
                .lon(dto.getLon())
                .build();
    }

    public LocationDto toLocationDto(Location location) {
        return LocationDto.builder()
                .lat(location.getLat())
                .lon(location.getLon())
                .build();
    }

    private void updateEvent(Event event, UpdateEventRequest dto, CategoryRepository catRepo) {
        if (notBlank(dto.getDescription())) {
            event.setDescription(dto.getDescription());
        }
        if (notBlank(dto.getAnnotation())) {
            event.setAnnotation(dto.getAnnotation());
        }
        if (nonNull(dto.getEventDate())) {
            event.setEventDate(dto.getEventDate());
        }
        if (nonNull(dto.getPaid())) {
            event.setPaid(dto.getPaid());
        }
        if (notBlank(dto.getTitle())) {
            event.setTitle(dto.getTitle());
        }
        if (nonNull(dto.getCategory())) {
            final Category category = catRepo.findById(dto.getCategory())
                    .orElseThrow(() -> new CategoryNotFoundException(dto.getCategory()));
            event.setCategory(category);
        }
        if (nonNull(dto.getLocation())) {
            final Location eventLocation = event.getLocation();
            final LocationDto dtoLocation = dto.getLocation();
            eventLocation.setLat(dtoLocation.getLat());
            eventLocation.setLon(dtoLocation.getLon());
        }
        if (nonNull(dto.getRequestModeration())) {
            event.setRequestModeration(dto.getRequestModeration());
        }
        if (nonNull(dto.getParticipantLimit())) {
            event.setParticipantLimit(dto.getParticipantLimit());
        }
    }

    public void updateEventByUser(Event event, UpdateEventRequest dto, CategoryRepository catRepo) {
        if (event.getState() == PUBLISHED) {
            throw new EventPublishingException(event.getId());
        }
        if (nonNull(dto.getStateAction())) {
            switch (dto.getStateAction()) {
                case CANCEL_REVIEW: event.setState(CANCELED); break;
                case SEND_TO_REVIEW: event.setState(PENDING);
            }
            updateEvent(event, dto, catRepo);
        }
    }

    public void updateEventByAdmin(Event event, UpdateEventRequest dto, CategoryRepository catRepo) {
        if (nonNull(dto.getStateAction())) {
            switch (dto.getStateAction()) {
                case PUBLISH_EVENT:
                    if (event.getState() == PENDING) {
                        event.setState(PUBLISHED);
                        event.setPublishedOn(now());
                        break;
                    }
                    throw new EventPublishingException(event.getId());
                case REJECT_EVENT:
                    if (event.getState() == PUBLISHED) {
                        throw new EventCancelingException(event.getId());
                    }
                    event.setState(CANCELED);
                    break;
                default:
                    event.setState(CANCELED);
            }
        }
        updateEvent(event, dto, catRepo);
    }

    public EventShortDto toEventShortDto(Event event) {
        return EventShortDto.builder()
                .id(event.getId())
                .description(event.getDescription())
                .annotation(event.getAnnotation())
                .confirmedRequests(event.getConfirmedRequests())
                .eventDate(event.getEventDate())
                .initiator(UserMapper.toUserShortDto(event.getInitiator()))
                .paid(event.getPaid())
                .title(event.getTitle())
                .views(event.getViews())
                .category(CategoryMapper.toCategoryDto(event.getCategory()))
                .build();
    }

    public List<EventShortDto> toEventShortDto(Collection<Event> events) {
        return events.stream()
                .map(EventMapper::toEventShortDto)
                .collect(Collectors.toList());
    }
}