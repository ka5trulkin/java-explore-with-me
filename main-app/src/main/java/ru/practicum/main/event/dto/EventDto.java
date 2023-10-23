package ru.practicum.main.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.practicum.main.user.dto.UserShortDto;
import ru.practicum.main.validation.EventTime;
import ru.practicum.main.validation.EventTimeAdmin;
import ru.practicum.utils.marker.AdminInfo;
import ru.practicum.utils.marker.CreateInfo;
import ru.practicum.utils.marker.UpdateInfo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

import static ru.practicum.utils.Patterns.DATE_TIME;
import static ru.practicum.utils.message.ValidationMessage.*;

@SuperBuilder
@NoArgsConstructor
@Getter
public class EventDto {
    private Long id;
    @NotBlank(groups = CreateInfo.class, message = DESCRIPTION_SHOULD_NOT_BE_BLANK)
    @Size(groups = {CreateInfo.class, AdminInfo.class, UpdateInfo.class}, min = 20, max = 7000, message = DESCRIPTION_BAD_SIZE_MAX_50)
    private String description;
    @NotBlank(groups = CreateInfo.class, message = ANNOTATION_SHOULD_NOT_BE_BLANK)
    @Size(groups = {CreateInfo.class, AdminInfo.class, UpdateInfo.class}, min = 20, max = 2000, message = ANNOTATION_BAD_SIZE_MAX_50)
    private String annotation;
    private Integer confirmedRequests;
    @NotNull(groups = CreateInfo.class, message = EVENT_DATE_SHOULD_NOT_BE_NULL)
    @EventTime(groups = {CreateInfo.class, UpdateInfo.class})
    @EventTimeAdmin(groups = AdminInfo.class)
    @JsonFormat(pattern = DATE_TIME)
    private LocalDateTime eventDate;
    private UserShortDto initiator;
    private Boolean paid;
    @NotBlank(groups = CreateInfo.class, message = TITLE_SHOULD_NOT_BE_BLANK)
    @Size(groups = {CreateInfo.class, AdminInfo.class, UpdateInfo.class}, min = 3, max = 120, message = TITLE_BAD_SIZE_MAX_120)
    private String title;
    private Integer views;
}