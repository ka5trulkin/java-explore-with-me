package ru.practicum.stats.server.storage;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.practicum.stats.dto.dto.StatsHitFilter;
import ru.practicum.stats.server.model.StatsHit;

import javax.persistence.EntityManager;
import java.util.List;

import static ru.practicum.stats.server.model.QStatsHit.statsHit;

public interface StatsRepository extends JpaRepository<StatsHit, Long>, QuerydslPredicateExecutor<StatsHit> {
    default List<Tuple> getTuplesToResponse(BooleanExpression condition, StatsHitFilter filter, EntityManager manager) {
        final OrderSpecifier<Long> hits = filter.isUnique() ? statsHit.ip.countDistinct().desc() : statsHit.ip.count().desc();
        return new JPAQuery<Tuple>(manager)
                .select(statsHit.app,
                        statsHit.uri,
                        filter.isUnique() ? statsHit.ip.countDistinct() : statsHit.ip.count())
                .from(statsHit)
                .where(condition)
                .groupBy(statsHit.app, statsHit.uri)
                .orderBy(hits)
                .fetch();
    }
}