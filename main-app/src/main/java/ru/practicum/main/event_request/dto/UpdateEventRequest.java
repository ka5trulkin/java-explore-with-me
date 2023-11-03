package ru.practicum.main.event_request.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.practicum.main.event.dto.EventCreateDto;
import ru.practicum.main.event.model.StateAction;

@SuperBuilder
@NoArgsConstructor
@Getter
public class UpdateEventRequest extends EventCreateDto {
    private StateAction stateAction;
}