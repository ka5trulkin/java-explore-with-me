package ru.practicum.stats.dto.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;

import static ru.practicum.stats.dto.util.ValidationMessage.APP_SHOULD_NOT_BE_BLANK;
import static ru.practicum.stats.dto.util.ValidationMessage.URI_SHOULD_NOT_BE_BLANK;

@SuperBuilder
@NoArgsConstructor
@Getter
public class StatsHitDto {
    @NotBlank(message = APP_SHOULD_NOT_BE_BLANK)
    private String app;
    @NotBlank(message = URI_SHOULD_NOT_BE_BLANK)
    private String uri;
}