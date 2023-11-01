package ru.practicum.stats.server.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.stats.dto.StatsHitCreate;
import ru.practicum.stats.dto.StatsHitFilter;
import ru.practicum.stats.dto.StatsHitResponse;
import ru.practicum.stats.server.service.StatsService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static ru.practicum.utils.Patterns.*;
import static ru.practicum.utils.message.LogMessage.REQUEST_GET_STATS;
import static ru.practicum.utils.message.LogMessage.REQUEST_POST_HIT;

@RestController
@Slf4j
@RequiredArgsConstructor
public class StatsController {
    private final StatsService statsService;

    @PostMapping(HIT_PREFIX)
    @ResponseStatus(CREATED)
    public void postHit(@Valid @RequestBody StatsHitCreate statHitCreate) {
        log.info(REQUEST_POST_HIT, statHitCreate.getUri());
        statsService.postStatsHit(statHitCreate);
    }
//
    @GetMapping(STATS_PREFIX)
    public List<StatsHitResponse> getStats(
            @RequestParam @DateTimeFormat(pattern = DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(pattern = DATE_TIME) LocalDateTime end,
            @RequestParam(required = false) List<String> uris,
            @RequestParam(defaultValue = "false") boolean unique
    ) {
        log.info(REQUEST_GET_STATS, start, end);
        return statsService.getStats(StatsHitFilter.of(start, end, uris, unique));
    }
}