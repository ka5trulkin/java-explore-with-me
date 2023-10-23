package ru.practicum.main.compilation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.compilation.dto.CompilationDto;
import ru.practicum.main.compilation.dto.NewCompilationDto;
import ru.practicum.main.compilation.service.CompilationService;
import ru.practicum.utils.marker.CreateInfo;
import ru.practicum.utils.marker.UpdateInfo;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static ru.practicum.utils.Patterns.COMPILATION_ADMIN_PREFIX;
import static ru.practicum.utils.message.LogMessage.*;

@RestController
@Slf4j
@RequestMapping(path = COMPILATION_ADMIN_PREFIX)
@RequiredArgsConstructor
public class CompilationAdminController {
    private final CompilationService compilationService;

    @PostMapping
    @ResponseStatus(CREATED)
    public CompilationDto postCompilation(@Validated(CreateInfo.class) @RequestBody NewCompilationDto dto) {
        log.info(REQUEST_ADD_COMPILATION, dto.getTitle());
        return compilationService.postCompilation(dto);
    }

    @PatchMapping("/{compId}")
    public CompilationDto updateCompilation(
            @PathVariable Long compId,
            @Validated(UpdateInfo.class) @RequestBody NewCompilationDto dto
    ) {
        log.info(REQUEST_UPDATE_COMPILATION, compId);
        return compilationService.updateCompilation(compId, dto);
    }

    @DeleteMapping("/{compId}")
    @ResponseStatus(NO_CONTENT)
    public void deleteCompilation(@PathVariable Long compId) {
        log.info(REQUEST_DELETE_COMPILATION, compId);
        compilationService.deleteCompilation(compId);
    }
}