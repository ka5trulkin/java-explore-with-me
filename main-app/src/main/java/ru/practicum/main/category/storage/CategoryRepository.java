package ru.practicum.main.category.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.main.category.mogel.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}