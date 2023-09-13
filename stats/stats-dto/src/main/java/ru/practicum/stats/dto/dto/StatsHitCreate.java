package ru.practicum.stats.dto.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

import static ru.practicum.stats.dto.pattern.StatsPattern.DATE_TIME;
import static ru.practicum.stats.dto.pattern.StatsPattern.IP_PATTERN;
import static ru.practicum.stats.dto.util.ValidationMessage.*;

@SuperBuilder
@NoArgsConstructor
@Getter
public class StatsHitCreate extends StatsHitDto {
    @NotBlank(message = IP_SHOULD_NOT_BE_BLANK)
    @Pattern(regexp = IP_PATTERN, message = IP_FORMAT_ERROR)
    private String ip;
    @NotNull(message = TIMESTAMP_SHOULD_NOT_BE_NULL)
    @PastOrPresent(message = TIME_SHOULD_NOT_BE_FUTURE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME)
    private LocalDateTime timestamp;
}