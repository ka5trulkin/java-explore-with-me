package ru.practicum.main.compilation.service;

import org.springframework.data.domain.PageRequest;
import ru.practicum.main.compilation.dto.CompilationDto;
import ru.practicum.main.compilation.dto.NewCompilationDto;

import java.util.List;

public interface CompilationService {
    CompilationDto postCompilation(NewCompilationDto dto);

    CompilationDto updateCompilation(Long compId, NewCompilationDto dto);

    void deleteCompilation(Long compId);

    CompilationDto getCompilation(Long compId);

    List<CompilationDto> getCompilationList(Boolean pinned, PageRequest page);
}