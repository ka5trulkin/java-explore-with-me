package ru.practicum.main.category.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import static ru.practicum.utils.message.ValidationMessage.NAME_BAD_SIZE_MAX_50;
import static ru.practicum.utils.message.ValidationMessage.NAME_SHOULD_NOT_BE_BLANK;

@SuperBuilder
@NoArgsConstructor
@Getter
public class CategoryDto {
    private Long id;
    @NotBlank(message = NAME_SHOULD_NOT_BE_BLANK)
    @Size(min = 1, max = 50, message = NAME_BAD_SIZE_MAX_50)
    private String name;
}