package ru.practicum.main.event_request.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.practicum.main.event_request.model.Status;

import java.time.LocalDateTime;

import static ru.practicum.utils.Patterns.DATE_TIME;

@SuperBuilder
@NoArgsConstructor
@Getter
public class ParticipationRequestDto {
    private Long id;
    @JsonFormat(pattern = DATE_TIME)
    private LocalDateTime created;
    private Long event;
    private Long requester;
    private Status status;
}