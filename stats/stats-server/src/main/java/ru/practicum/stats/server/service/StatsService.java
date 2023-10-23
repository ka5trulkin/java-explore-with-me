package ru.practicum.stats.server.service;

import ru.practicum.stats.dto.StatsHitCreate;
import ru.practicum.stats.dto.StatsHitFilter;
import ru.practicum.stats.dto.StatsHitResponse;

import java.util.List;

public interface StatsService {
    void postStatsHit(StatsHitCreate statHitCreate);

    List<StatsHitResponse> getStats(StatsHitFilter filter);
}