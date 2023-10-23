package ru.practicum.main.compilation.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@NoArgsConstructor
@Getter
public class NewCompilationDto extends AbstractCompilationDto {
    private List<Long> events;
}