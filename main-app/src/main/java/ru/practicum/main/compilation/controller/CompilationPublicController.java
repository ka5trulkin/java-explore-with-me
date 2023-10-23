package ru.practicum.main.compilation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.compilation.dto.CompilationDto;
import ru.practicum.main.compilation.service.CompilationService;
import ru.practicum.utils.PageApp;

import java.util.List;

import static ru.practicum.utils.Patterns.COMPILATION_PUBLIC_PREFIX;
import static ru.practicum.utils.message.LogMessage.REQUEST_GET_COMPILATION;
import static ru.practicum.utils.message.LogMessage.REQUEST_GET_COMPILATION_LIST;

@RestController
@Slf4j
@RequestMapping(path = COMPILATION_PUBLIC_PREFIX)
@RequiredArgsConstructor
public class CompilationPublicController {
    private final CompilationService compilationService;

    @GetMapping("/{compId}")
    public CompilationDto getCompilation(@PathVariable Long compId) {
        log.info(REQUEST_GET_COMPILATION, compId);
        return compilationService.getCompilation(compId);
    }

    @GetMapping
    public List<CompilationDto> getCompilationList(
            @RequestParam(required = false) Boolean pinned,
            @RequestParam(defaultValue = "0") Integer from,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        log.info(REQUEST_GET_COMPILATION_LIST);
        return compilationService.getCompilationList(pinned, PageApp.ofStartingIndex(from, size));
    }
}