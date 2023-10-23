package ru.practicum.main.event.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.practicum.main.validation.LocationFields;
import ru.practicum.utils.marker.CreateInfo;
import ru.practicum.utils.marker.UpdateInfo;

import javax.validation.constraints.NotNull;

import static ru.practicum.utils.message.ValidationMessage.CATEGORY_SHOULD_NOT_BE_BLANK;
import static ru.practicum.utils.message.ValidationMessage.LOCATION_SHOULD_NOT_BE_BLANK;

@SuperBuilder
@NoArgsConstructor
@Getter
public class EventCreateDto extends EventDto {
    @NotNull(groups = CreateInfo.class, message = CATEGORY_SHOULD_NOT_BE_BLANK)
    private Long category;
    @NotNull(groups = CreateInfo.class, message = LOCATION_SHOULD_NOT_BE_BLANK)
    @LocationFields(groups = {CreateInfo.class, UpdateInfo.class})
    private LocationDto location;
    private Boolean requestModeration;
    private Integer participantLimit;
}