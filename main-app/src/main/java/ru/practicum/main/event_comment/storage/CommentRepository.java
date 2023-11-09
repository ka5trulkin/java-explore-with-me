package ru.practicum.main.event_comment.storage;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.lang.NonNull;
import ru.practicum.main.event_comment.model.Comment;

import java.util.Optional;

import static ru.practicum.utils.Patterns.COMMENT_WITH_FIELDS;

public interface CommentRepository extends JpaRepository<Comment, Long>, QuerydslPredicateExecutor<Comment> {
    @Override
    @EntityGraph(COMMENT_WITH_FIELDS)
    Optional<Comment> findOne(@NonNull Predicate predicate);

    @Override
    @EntityGraph(COMMENT_WITH_FIELDS)
    Page<Comment> findAll(@NonNull Predicate predicate, @NonNull Pageable pageable);
}