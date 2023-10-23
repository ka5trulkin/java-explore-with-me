package ru.practicum.main.category.service;

import org.springframework.data.domain.PageRequest;
import ru.practicum.main.category.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto addCategory(CategoryDto dto);

    CategoryDto updateCategory(Long id, CategoryDto dto);

    void deleteCategory(Long id);

    CategoryDto getCategory(Long id);

    List<CategoryDto> detCategoryList(PageRequest page);
}