package ru.practicum.main.event_request.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.practicum.main.event_request.model.Status;

import java.util.List;

@SuperBuilder
@NoArgsConstructor
@Getter
public class EventRequestStatusUpdateRequest {
    private List<Long> requestIds;
    private Status status;
}