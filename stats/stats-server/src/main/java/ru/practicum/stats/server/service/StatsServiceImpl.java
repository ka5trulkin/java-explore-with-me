package ru.practicum.stats.server.service;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.stats.dto.StatsHitCreate;
import ru.practicum.stats.dto.StatsHitFilter;
import ru.practicum.stats.dto.StatsHitResponse;
import ru.practicum.stats.server.exception.BadEventTimeException;
import ru.practicum.stats.server.mapper.StatsMapper;
import ru.practicum.stats.server.storage.StatsRepository;
import ru.practicum.utils.QPredicate;

import javax.persistence.EntityManager;
import java.util.List;

import static ru.practicum.stats.server.model.QStatsHit.statsHit;
import static ru.practicum.utils.message.ExceptionMessage.EVENT_START_END_BAD;
import static ru.practicum.utils.message.LogMessage.POST_HIT;
import static ru.practicum.utils.message.LogMessage.STATS_GET;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StatsServiceImpl implements StatsService {
    private final EntityManager entityManager;
    private final StatsRepository statsRepo;

    @Override
    @Transactional
    public void postStatsHit(StatsHitCreate statHitCreate) {
        statsRepo.save(StatsMapper.toStatsHit(statHitCreate));
        log.info(POST_HIT, statHitCreate.getUri());
    }

    @Override
    public List<StatsHitResponse> getStats(StatsHitFilter filter) {
        if (filter.getStart().isAfter(filter.getEnd())) {
            throw new BadEventTimeException(EVENT_START_END_BAD);
        }
        final Predicate predicate = QPredicate.builder()
                .add(statsHit.timestamp.between(filter.getStart(), filter.getEnd()))
                .add(filter.getUris(), statsHit.uri::in)
                .buildAnd();
        final List<Tuple> tuplesToResponse = statsRepo.getTuplesToResponse(predicate, filter, entityManager);
        log.info(STATS_GET, filter.getStart(), filter.getEnd());
        return StatsMapper.toStatsHitResponse(tuplesToResponse);
    }
}