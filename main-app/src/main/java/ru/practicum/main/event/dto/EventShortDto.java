package ru.practicum.main.event.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.practicum.main.category.dto.CategoryDto;

@SuperBuilder
@NoArgsConstructor
@Getter
public class EventShortDto extends EventDto {
    private CategoryDto category;
}