package ru.practicum.main.event.storage;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.lang.NonNull;
import ru.practicum.main.event.model.Event;

import javax.persistence.EntityManager;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import static ru.practicum.main.category.mogel.QCategory.category;
import static ru.practicum.main.event.model.QEvent.event;
import static ru.practicum.main.user.model.QUser.user;
import static ru.practicum.utils.Patterns.EVENT_WITH_FIELDS;

public interface EventRepository extends JpaRepository<Event, Long>, QuerydslPredicateExecutor<Event> {
    default Tuple getUserAndCategory(Long userId, Long categoryId, EntityManager manager) {
        return new JPAQuery<Tuple>(manager)
                .select(user, category)
                .from(user, category)
                .where(user.id.eq(userId).and(category.id.eq(categoryId)))
                .fetchOne();
    }

    default Tuple getUserAndEvent(Predicate predicate , EntityManager manager) {
        return new JPAQuery<Tuple>(manager)
                .select(user, event)
                .from(user, event)
                .leftJoin(event.initiator).fetchJoin()
                .leftJoin(event.category).fetchJoin()
                .leftJoin(event.location).fetchJoin()
                .where(predicate)
                .fetchOne();
    }

    @Override
    @EntityGraph(EVENT_WITH_FIELDS)
    Optional<Event> findOne(@NonNull Predicate predicate);

    @Override
    @EntityGraph(EVENT_WITH_FIELDS)
    Page<Event> findAll(@NonNull Predicate predicate, @NonNull Pageable pageable);

    @EntityGraph(EVENT_WITH_FIELDS)
    Set<Event> findByIdIn(Collection<Long> ids);
}