package ru.practicum.stats.server.service;

import ru.practicum.stats.dto.dto.StatsHitCreate;
import ru.practicum.stats.dto.dto.StatsHitFilter;
import ru.practicum.stats.dto.dto.StatsHitResponse;

import java.util.List;

public interface StatsService {
    void addStatsHit(StatsHitCreate statHitCreate);

    List<StatsHitResponse> getStats(StatsHitFilter filter);
}