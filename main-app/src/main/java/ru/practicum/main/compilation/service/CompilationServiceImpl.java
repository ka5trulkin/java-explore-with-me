package ru.practicum.main.compilation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.compilation.dto.CompilationDto;
import ru.practicum.main.compilation.dto.NewCompilationDto;
import ru.practicum.main.compilation.mapper.CompilationMapper;
import ru.practicum.main.compilation.model.Compilation;
import ru.practicum.main.compilation.storage.CompilationRepository;
import ru.practicum.main.event.storage.EventRepository;
import ru.practicum.main.exception.child.CompilationNotFoundException;

import java.util.List;

import static ru.practicum.main.compilation.model.QCompilation.compilation;
import static ru.practicum.utils.message.LogMessage.*;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {
    private final CompilationRepository compilationRepo;
    private final EventRepository eventRepo;

    private Compilation getCompilationOrThrow(Long compId) {
        return compilationRepo.findById(compId)
                .orElseThrow(() -> new CompilationNotFoundException(compId));
    }

    @Override
    @Transactional
    public CompilationDto postCompilation(NewCompilationDto dto) {
        final Compilation compilation = CompilationMapper.toCompilation(dto, eventRepo);
        compilationRepo.save(compilation);
        log.info(COMPILATION_ADDED, compilation.getId(), compilation.getTitle());
        return CompilationMapper.toCompilationDto(compilation);
    }

    @Override
    @Transactional
    public CompilationDto updateCompilation(Long compId, NewCompilationDto dto) {
        final Compilation compilation = this.getCompilationOrThrow(compId);
        CompilationMapper.updateCompilation(compilation, dto, eventRepo);
        log.info(COMPILATION_UPDATED, compId);
        return CompilationMapper.toCompilationDto(compilation);
    }

    @Override
    @Transactional
    public void deleteCompilation(Long compId) {
        final boolean isExists = compilationRepo.existsById(compId);
        if (!isExists) {
            throw new CompilationNotFoundException(compId);
        }
        compilationRepo.deleteById(compId);
        log.info(COMPILATION_DELETED, compId);
    }

    @Override
    public CompilationDto getCompilation(Long compId) {
        final Compilation compilation = this.getCompilationOrThrow(compId);
        log.info(GET_COMPILATION, compId);
        return CompilationMapper.toCompilationDto(compilation);
    }

    @Override
    public List<CompilationDto> getCompilationList(Boolean pinned, PageRequest page) {
        final List<Compilation> compilations = pinned == null
                ? compilationRepo.findAll(page).toList()
                : compilationRepo.findAll(compilation.pinned.eq(pinned), page).toList();
        log.info(GET_COMPILATION_LIST);
        return CompilationMapper.toCompilationDto(compilations);
    }
}