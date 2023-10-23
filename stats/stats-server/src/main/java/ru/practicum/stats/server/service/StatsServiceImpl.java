package ru.practicum.stats.server.service;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.stats.dto.dto.StatsHitCreate;
import ru.practicum.stats.dto.dto.StatsHitFilter;
import ru.practicum.stats.dto.dto.StatsHitResponse;
import ru.practicum.stats.server.mapper.StatsMapper;
import ru.practicum.stats.server.storage.StatsRepository;

import javax.persistence.EntityManager;
import java.util.List;

import static ru.practicum.stats.server.model.QStatsHit.statsHit;
import static ru.practicum.stats.server.util.LogMessage.GET_STATS;
import static ru.practicum.stats.server.util.LogMessage.POST_HIT;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StatsServiceImpl implements StatsService {
    private final EntityManager entityManager;
    private final StatsRepository statsRepository;

    private BooleanExpression getCondition(StatsHitFilter filter) {
        BooleanExpression condition = statsHit.timestamp.between(filter.getStart(), filter.getEnd());
        final List<String> uris = filter.getUris();
        if (uris != null && !uris.isEmpty()) {
            condition = condition.and(statsHit.uri.in(uris));
        }
        return condition;
    }

    @Override
    @Transactional
    public void addStatsHit(StatsHitCreate statHitCreate) {
        statsRepository.save(StatsMapper.toStatsHit(statHitCreate));
        log.info(POST_HIT, statHitCreate.getUri());
    }

    @Override
    public List<StatsHitResponse> getStats(StatsHitFilter filter) {
        final BooleanExpression condition = this.getCondition(filter);
        final List<Tuple> tuplesToResponse = statsRepository.getTuplesToResponse(condition, filter, entityManager);
        log.info(GET_STATS, filter.getStart(), filter.getEnd());
        return StatsMapper.toStatsHitResponse(tuplesToResponse);
    }
}