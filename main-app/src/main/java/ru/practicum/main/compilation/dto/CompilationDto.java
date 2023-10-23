package ru.practicum.main.compilation.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.practicum.main.event.dto.EventShortDto;

import java.util.List;

@SuperBuilder
@NoArgsConstructor
@Getter
public class CompilationDto extends AbstractCompilationDto {
    private Long id;
    private List<EventShortDto> events;
}