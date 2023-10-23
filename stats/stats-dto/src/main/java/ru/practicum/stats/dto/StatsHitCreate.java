package ru.practicum.stats.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;

import static ru.practicum.utils.Patterns.DATE_TIME;
import static ru.practicum.utils.message.ValidationMessage.*;

@SuperBuilder
@NoArgsConstructor
@Getter
public class StatsHitCreate extends StatsHitDto {
    @NotBlank(message = IP_SHOULD_NOT_BE_BLANK)
    private String ip;
    @NotNull(message = TIMESTAMP_SHOULD_NOT_BE_NULL)
    @PastOrPresent(message = TIME_SHOULD_NOT_BE_FUTURE)
    @JsonFormat(pattern = DATE_TIME)
    private LocalDateTime timestamp;
}