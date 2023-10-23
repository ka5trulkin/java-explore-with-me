package ru.practicum.main.event.model;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public enum Sort {
    EVENT_DATE, VIEWS;

    public static List<Event> sortBy(List<Event> events, Sort sort) {
        if (sort != null) {
            switch (sort) {
                case EVENT_DATE:
                    return events.stream()
                            .sorted(Comparator.comparing(Event::getEventDate).reversed())
                            .collect(Collectors.toList());
                case VIEWS:
                    return events.stream()
                            .sorted(Comparator.comparing(Event::getViews).reversed())
                            .collect(Collectors.toList());
            }
        }
        return events;
    }
}