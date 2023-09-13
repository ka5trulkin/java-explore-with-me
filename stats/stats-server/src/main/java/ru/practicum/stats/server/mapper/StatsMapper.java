package ru.practicum.stats.server.mapper;

import com.querydsl.core.Tuple;
import lombok.experimental.UtilityClass;
import ru.practicum.stats.dto.dto.StatsHitCreate;
import ru.practicum.stats.dto.dto.StatsHitResponse;
import ru.practicum.stats.server.model.StatsHit;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class StatsMapper {
    public StatsHit toStatsHit(StatsHitCreate statHitCreate) {
        return StatsHit.builder()
                .app(statHitCreate.getApp())
                .uri(statHitCreate.getUri())
                .ip(statHitCreate.getIp())
                .timestamp(statHitCreate.getTimestamp())
                .build();
    }

    public StatsHitResponse toStatsHitResponse(Tuple tuple) {
        final String app = tuple.get(0, String.class);
        final String uri = tuple.get(1, String.class);
        final Long hits = tuple.get(2, Long.class);
        return StatsHitResponse.builder()
                .app(app)
                .uri(uri)
                .hits(hits != null ? hits : 0)
                .build();
    }

    public List<StatsHitResponse> toStatsHitResponse(Collection<Tuple> tuples) {
        return tuples.stream()
                .map(StatsMapper::toStatsHitResponse)
                .collect(Collectors.toList());
    }
}