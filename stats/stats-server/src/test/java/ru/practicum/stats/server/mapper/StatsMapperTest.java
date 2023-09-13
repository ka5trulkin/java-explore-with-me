package ru.practicum.stats.server.mapper;

import com.querydsl.core.Tuple;
import org.junit.jupiter.api.Test;
import ru.practicum.stats.dto.dto.StatsHitCreate;
import ru.practicum.stats.dto.dto.StatsHitResponse;
import ru.practicum.stats.server.model.StatsHit;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class StatsMapperTest extends AbstractTest {

    @Test
    void toStatsHit() {
        final StatsHit expected = getStatHitNoId();
        final StatsHitCreate statsHitCreate = getStatsHitCreate();
        final StatsHit actual = StatsMapper.toStatsHit(statsHitCreate);

        assertThat(actual.getApp()).isEqualTo(expected.getApp());
        assertThat(actual.getUri()).isEqualTo(expected.getUri());
        assertThat(actual.getIp()).isEqualTo(expected.getIp());
        assertThat(actual.getTimestamp()).isEqualTo(expected.getTimestamp());
    }

    @Test
    void toStatsHitResponse() {
        final Tuple tuple = getTuple();
        final StatsHitResponse expected = getStatsHitResponse();
        final StatsHitResponse actual = StatsMapper.toStatsHitResponse(tuple);

        assertThat(actual.getApp()).isEqualTo(expected.getApp());
        assertThat(actual.getUri()).isEqualTo(expected.getUri());
        assertThat(actual.getHits()).isEqualTo(expected.getHits());
    }

    @Test
    void toStatsHitResponseList() {
        final List<StatsHitResponse> expected = List.of(getStatsHitResponse());
        final List<StatsHitResponse> actual = StatsMapper.toStatsHitResponse(List.of(getTuple()));

        assertThat(actual).isNotEmpty();
        assertThat(actual).hasSize(1);
        assertThat(actual.get(0).getApp()).isEqualTo(expected.get(0).getApp());
        assertThat(actual.get(0).getUri()).isEqualTo(expected.get(0).getUri());
        assertThat(actual.get(0).getHits()).isEqualTo(expected.get(0).getHits());
    }
}