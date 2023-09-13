package ru.practicum.stats.server.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.stats.dto.dto.StatsHitCreate;
import ru.practicum.stats.dto.dto.StatsHitFilter;
import ru.practicum.stats.dto.dto.StatsHitResponse;
import ru.practicum.stats.server.service.StatsService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static ru.practicum.stats.dto.pattern.StatsPattern.DATE_TIME;
import static ru.practicum.stats.server.util.LogMessage.REQUEST_GET_STATS;
import static ru.practicum.stats.server.util.LogMessage.REQUEST_POST_HIT;

@Slf4j
@RestController
@RequiredArgsConstructor
public class StatsController {
    private final StatsService statsService;

    @PostMapping("/hit")
    @ResponseStatus(CREATED)
    public void postHit(@Valid @RequestBody StatsHitCreate statHitCreate) {
        log.info(REQUEST_POST_HIT, statHitCreate.getUri());
        statsService.addStatsHit(statHitCreate);
    }

    @GetMapping("/stats")
    public List<StatsHitResponse> getStats(@RequestParam @DateTimeFormat(pattern = DATE_TIME) LocalDateTime start,
                                           @RequestParam @DateTimeFormat(pattern = DATE_TIME) LocalDateTime end,
                                           @RequestParam(required = false) List<String> uris,
                                           @RequestParam(defaultValue = "false") boolean unique) {
        log.info(REQUEST_GET_STATS, start, end);
        return statsService.getStats(StatsHitFilter.of(start, end, uris, unique));
    }
}