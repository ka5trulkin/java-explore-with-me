package ru.practicum.main.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import static ru.practicum.utils.message.ValidationMessage.EMAIL_BAD_SIZE_MAX_250;
import static ru.practicum.utils.message.ValidationMessage.NAME_SHOULD_NOT_BE_BLANK;

@SuperBuilder
@NoArgsConstructor
@Getter
public class UserShortDto {
    private Long id;
    @NotBlank(message = NAME_SHOULD_NOT_BE_BLANK)
    @Size(min = 2, max = 250, message = EMAIL_BAD_SIZE_MAX_250)
    private String name;
}