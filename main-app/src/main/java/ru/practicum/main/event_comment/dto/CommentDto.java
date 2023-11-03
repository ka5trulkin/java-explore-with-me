package ru.practicum.main.event_comment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.practicum.main.user.dto.UserShortDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

import static ru.practicum.utils.Patterns.DATE_TIME;
import static ru.practicum.utils.message.ValidationMessage.TEXT_BAD_SIZE_MAX_2000;
import static ru.practicum.utils.message.ValidationMessage.TEXT_SHOULD_NOT_BE_BLANK;

@SuperBuilder
@NoArgsConstructor
@Getter
public class CommentDto {
    private Long id;
    @JsonFormat(pattern = DATE_TIME)
    private LocalDateTime createdOn;
    @NotBlank(message = TEXT_SHOULD_NOT_BE_BLANK)
    @Size(min = 1, max = 2000, message = TEXT_BAD_SIZE_MAX_2000)
    private String text;
    private Long event;
    private UserShortDto commentator;
}