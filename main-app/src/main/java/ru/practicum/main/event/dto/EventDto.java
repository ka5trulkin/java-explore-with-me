package ru.practicum.main.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.practicum.main.user.dto.UserShortDto;
import ru.practicum.main.validation.EventTime;
import ru.practicum.main.validation.EventTimeAdmin;
import ru.practicum.utils.marker.AdminInfo;
import ru.practicum.utils.marker.CreateInfo;
import ru.practicum.utils.marker.UpdateInfo;

import javax.validation.Valid;
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
    @JsonUnwrapped
    @Valid
    private Text text;
    private Integer confirmedRequests;
    private UserShortDto initiator;
    private Boolean paid;
    private Integer views;
    @NotNull(groups = CreateInfo.class, message = EVENT_DATE_SHOULD_NOT_BE_NULL)
    @EventTime(groups = {CreateInfo.class, UpdateInfo.class})
    @EventTimeAdmin(groups = AdminInfo.class)
    @JsonFormat(pattern = DATE_TIME)
    private LocalDateTime eventDate;

    public String getDescription() {
        return text.getDescription();
    }

    public String getAnnotation() {
        return text.getAnnotation();
    }

    public String getTitle() {
        return text.getTitle();
    }

    public static Text textOf(String description, String annotation, String title) {
        return Text.builder()
                .description(description)
                .annotation(annotation)
                .title(title)
                .build();
    }

    @SuperBuilder
    @NoArgsConstructor
    @Getter
    public static class Text {
        @NotBlank(groups = CreateInfo.class, message = DESCRIPTION_SHOULD_NOT_BE_BLANK)
        @Size(groups = {CreateInfo.class, AdminInfo.class, UpdateInfo.class}, min = 20, max = 7000, message = DESCRIPTION_BAD_SIZE_MAX_50)
        private String description;
        @NotBlank(groups = CreateInfo.class, message = ANNOTATION_SHOULD_NOT_BE_BLANK)
        @Size(groups = {CreateInfo.class, AdminInfo.class, UpdateInfo.class}, min = 20, max = 2000, message = ANNOTATION_BAD_SIZE_MAX_50)
        private String annotation;
        @NotBlank(groups = CreateInfo.class, message = TITLE_SHOULD_NOT_BE_BLANK)
        @Size(groups = {CreateInfo.class, AdminInfo.class, UpdateInfo.class}, min = 3, max = 120, message = TITLE_BAD_SIZE_MAX_120)
        private String title;
    }
}