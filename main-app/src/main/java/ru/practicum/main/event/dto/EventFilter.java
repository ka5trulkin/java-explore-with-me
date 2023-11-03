package ru.practicum.main.event.dto;

import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.data.domain.PageRequest;
import ru.practicum.main.event.model.EventSort;
import ru.practicum.main.event.model.State;
import ru.practicum.main.exception.base.RequestException;

import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.main.event.model.EventSort.sortBy;
import static ru.practicum.utils.PageApp.ofStartingIndex;
import static ru.practicum.utils.message.ExceptionMessage.EVENT_START_END_BAD;

@SuperBuilder
@Getter
@ToString
public class EventFilter {
    private List<Long> users;
    private List<State> states;
    private List<Long> categories;
    private LocalDateTime rangeStart;
    private LocalDateTime rangeEnd;
    private PageRequest eventPage;
    private String text;
    private Boolean paid;
    private Boolean onlyAvailable;
    private EventSort sort;

    private static void checkTimeIsValid(LocalDateTime rangeStart, LocalDateTime rangeEnd) {
        if ((rangeStart != null) && (rangeEnd != null) && rangeStart.isAfter(rangeEnd)) {
            throw new RequestException(EVENT_START_END_BAD);
        }
    }

    public static EventFilter of(
            String text,
            List<Long> categories,
            Boolean paid,
            LocalDateTime rangeStart,
            LocalDateTime rangeEnd,
            Boolean onlyAvailable,
            EventSort sort,
            Integer from,
            Integer size
    ) {
        checkTimeIsValid(rangeStart, rangeEnd);
        return EventFilter.builder()
                .text(text)
                .categories(categories)
                .paid(paid)
                .rangeStart(rangeStart)
                .rangeEnd(rangeEnd)
                .onlyAvailable(onlyAvailable)
                .sort(sort)
                .eventPage(ofStartingIndex(from, size, sortBy(sort)))
                .build();
    }

    public static EventFilter of(
            List<Long> users,
            List<State> states,
            List<Long> categories,
            LocalDateTime rangeStart,
            LocalDateTime rangeEnd,
            Integer from,
            Integer size
    ) {
        checkTimeIsValid(rangeStart, rangeEnd);
        return EventFilter.builder()
                .users(users)
                .states(states)
                .categories(categories)
                .rangeStart(rangeStart)
                .rangeEnd(rangeEnd)
                .eventPage(ofStartingIndex(from, size))
                .build();
    }
}