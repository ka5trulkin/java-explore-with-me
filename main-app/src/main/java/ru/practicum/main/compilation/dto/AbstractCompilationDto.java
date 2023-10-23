package ru.practicum.main.compilation.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.practicum.utils.marker.CreateInfo;
import ru.practicum.utils.marker.UpdateInfo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import static ru.practicum.utils.message.ValidationMessage.TITLE_BAD_SIZE_MAX_50;
import static ru.practicum.utils.message.ValidationMessage.TITLE_SHOULD_NOT_BE_BLANK;

@SuperBuilder
@NoArgsConstructor
@Getter
public abstract class AbstractCompilationDto {
    private Boolean pinned;
    @NotBlank(groups = CreateInfo.class, message = TITLE_SHOULD_NOT_BE_BLANK)
    @Size(groups = {CreateInfo.class, UpdateInfo.class}, min = 1, max = 50, message = TITLE_BAD_SIZE_MAX_50)
    private String title;
}