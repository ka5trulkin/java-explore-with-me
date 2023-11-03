package ru.practicum.main.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.practicum.main.event.model.State;

import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.utils.Patterns.DATE_TIME;

@SuperBuilder
@NoArgsConstructor
@Getter
public class EventFullDto extends EventShortDto {
    private LocationDto location;
    private Boolean requestModeration;
    private Integer participantLimit;
    @JsonFormat(pattern = DATE_TIME)
    private LocalDateTime createdOn;
    @JsonFormat(pattern = DATE_TIME)
    private LocalDateTime publishedOn;
    private State state;
    private List<CommentDto> comments;
}