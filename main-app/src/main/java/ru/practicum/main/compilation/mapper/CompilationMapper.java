package ru.practicum.main.compilation.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.main.compilation.dto.CompilationDto;
import ru.practicum.main.compilation.dto.NewCompilationDto;
import ru.practicum.main.compilation.model.Compilation;
import ru.practicum.main.event.mapper.EventMapper;
import ru.practicum.main.event.storage.EventRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@UtilityClass
public class CompilationMapper {
    private boolean notBlank(String string) {
        return string != null && !string.isBlank();
    }

    public Compilation toCompilation(NewCompilationDto dto, EventRepository eventRepo) {
        final Compilation compilation = Compilation.builder()
                .pinned(dto.getPinned() != null && dto.getPinned())
                .title(dto.getTitle())
                .build();
        if (dto.getEvents() != null && !dto.getEvents().isEmpty()) {
            compilation.setEvents(eventRepo.findByIdIn(dto.getEvents()));
        }
        return compilation;
    }

    public CompilationDto toCompilationDto(Compilation compilation) {
        return CompilationDto.builder()
                .id(compilation.getId())
                .pinned(compilation.getPinned())
                .title(compilation.getTitle())
                .events(compilation.getEvents() == null ? Collections.emptyList() : EventMapper.toEventShortDto(compilation.getEvents()))
                .build();
    }

    public List<CompilationDto> toCompilationDto(Collection<Compilation> compilations) {
        return compilations.stream()
                .map(CompilationMapper::toCompilationDto)
                .collect(Collectors.toList());
    }

    public void updateCompilation(Compilation compilation, NewCompilationDto dto, EventRepository eventRepo) {
        if (nonNull(dto.getPinned())) {
            compilation.setPinned(dto.getPinned());
        }
        if (notBlank(dto.getTitle())) {
            compilation.setTitle(dto.getTitle());
        }
        if (dto.getEvents() != null && !dto.getEvents().isEmpty()) {
            compilation.setEvents(eventRepo.findByIdIn(dto.getEvents()));
        }
    }
}