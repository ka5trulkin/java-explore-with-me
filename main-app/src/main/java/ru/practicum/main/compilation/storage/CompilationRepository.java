package ru.practicum.main.compilation.storage;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.lang.NonNull;
import ru.practicum.main.compilation.model.Compilation;

import static ru.practicum.utils.Patterns.COMPILATION_WITH_FIELDS;

public interface CompilationRepository extends JpaRepository<Compilation, Long>, QuerydslPredicateExecutor<Compilation> {
    @Override
    @EntityGraph(value = COMPILATION_WITH_FIELDS)
    Page<Compilation> findAll(@NonNull Pageable pageable);

    @Override
    @EntityGraph(value = COMPILATION_WITH_FIELDS)
    Page<Compilation> findAll(@NonNull Predicate predicate, @NonNull Pageable pageable);
}