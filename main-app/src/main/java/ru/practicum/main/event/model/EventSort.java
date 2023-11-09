package ru.practicum.main.event.model;

import org.springframework.data.domain.Sort;

public enum EventSort {
    EVENT_DATE, VIEWS, COMMENT_DATE;

    public static Sort sortBy(EventSort sort) {
        if (sort != null) {
            switch (sort) {
                case EVENT_DATE:
                case COMMENT_DATE: return Sort.by("createdOn").descending();
                case VIEWS: return Sort.by("views").descending();
            }
        }
        return Sort.unsorted();
    }
}