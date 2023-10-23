package ru.practicum.main.event_request.storage;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.lang.NonNull;
import ru.practicum.main.event_request.model.EventRequest;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static ru.practicum.main.event.model.QEvent.event;
import static ru.practicum.main.user.model.QUser.user;
import static ru.practicum.utils.Patterns.EVENT_REQUEST_WITH_FIELDS;

public interface EventRequestRepository extends JpaRepository<EventRequest, Long>, QuerydslPredicateExecutor<EventRequest> {
    default Tuple getUserAndEvent(Long userId, Long eventId, EntityManager manager) {
        return new JPAQuery<Tuple>(manager)
                .select(user, event)
                .from(user, event)
                .leftJoin(event.initiator).fetchJoin()
                .leftJoin(event.category).fetchJoin()
                .leftJoin(event.location).fetchJoin()
                .where(user.id.eq(userId).and(event.id.eq(eventId)))
                .fetchOne();
    }

    @EntityGraph(value = EVENT_REQUEST_WITH_FIELDS)
    List<EventRequest> findAll(@NonNull Predicate predicate);

    @EntityGraph(value = EVENT_REQUEST_WITH_FIELDS)
    Optional<EventRequest> findOne(@NonNull Predicate predicate);
}