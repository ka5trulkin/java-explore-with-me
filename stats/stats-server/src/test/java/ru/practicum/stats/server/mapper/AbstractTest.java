package ru.practicum.stats.server.mapper;

import com.querydsl.core.Tuple;
import org.mockito.Mockito;
import ru.practicum.stats.dto.dto.StatsHitCreate;
import ru.practicum.stats.dto.dto.StatsHitResponse;
import ru.practicum.stats.server.model.StatsHit;

import java.time.LocalDateTime;

import static org.mockito.Mockito.when;

public abstract class AbstractTest {
    private final String someApp = "Some App";
    private final String someUri = "Some URI";
    private final String localhost = "127.0.0.1";
    private final Long hits = 3L;
    private final LocalDateTime yesterday = LocalDateTime.now().minusDays(1);

    protected StatsHit getStatHitNoId() {
        return StatsHit.builder()
                .app(someApp)
                .uri(someUri)
                .ip(localhost)
                .timestamp(yesterday)
                .build();
    }

    protected StatsHitCreate getStatsHitCreate() {
        return StatsHitCreate.builder()
                .app(someApp)
                .uri(someUri)
                .ip(localhost)
                .timestamp(yesterday)
                .build();
    }

    protected Tuple getTuple() {
        Tuple tuple = Mockito.mock(Tuple.class);
        when(tuple.get(0, String.class)).thenReturn(someApp);
        when(tuple.get(1, String.class)).thenReturn(someUri);
        when(tuple.get(2, Long.class)).thenReturn(hits);
        return tuple;
    }

    protected StatsHitResponse getStatsHitResponse() {
        return StatsHitResponse.builder()
                .app(someApp)
                .uri(someUri)
                .hits(hits)
                .build();
    }
}