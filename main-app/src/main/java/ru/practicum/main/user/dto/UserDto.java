package ru.practicum.main.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import static ru.practicum.utils.message.ValidationMessage.*;

@SuperBuilder
@NoArgsConstructor
@Getter
public class UserDto {
    private Long id;
    @NotBlank(message = NAME_SHOULD_NOT_BE_BLANK)
    @Size(min = 2, max = 250, message = NAME_BAD_SIZE_MAX_250)
    private String name;
    @NotBlank(message = EMAIL_SHOULD_NOT_BE_BLANK)
    @Email(message = EMAIL_BAD)
    @Size(min = 6, max = 254, message = EMAIL_BAD_SIZE_MAX_250)
    private String email;
}