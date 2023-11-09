package ru.practicum.main.event.mapper;

import com.querydsl.core.Tuple;
import ru.practicum.main.exception.base.NotFoundException;

public abstract class EventAbstractMapper {
    protected static void checkTupleOrThrow(Tuple tuple, String message) {
        if (tuple == null) {
            throw new NotFoundException(message);
        }
    }

    protected static boolean notBlank(String string) {
        return string != null && !string.isBlank();
    }
}