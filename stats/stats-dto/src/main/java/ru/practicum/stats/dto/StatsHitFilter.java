package ru.practicum.stats.dto;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@SuperBuilder
@Getter
public class StatsHitFilter {
    private LocalDateTime start;
    private LocalDateTime end;
    private List<String> uris;
    private boolean unique;

    public static StatsHitFilter of(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        return StatsHitFilter.builder()
                .start(start)
                .end(end)
                .uris(uris)
                .unique(unique)
                .build();
    }
}