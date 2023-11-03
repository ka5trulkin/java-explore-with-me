package ru.practicum.main.category.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.category.dto.CategoryDto;
import ru.practicum.main.category.mapper.CategoryMapper;
import ru.practicum.main.category.mogel.Category;
import ru.practicum.main.category.storage.CategoryRepository;
import ru.practicum.main.exception.child.CategoryNameAlreadyExistException;
import ru.practicum.main.exception.child.CategoryNotFoundException;

import java.util.List;

import static ru.practicum.utils.message.LogMessage.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepo;

    private Category saveWithNameCheck(Category category) {
        try {
            return categoryRepo.save(category);
        } catch (DataIntegrityViolationException e) {
            throw new CategoryNameAlreadyExistException(category.getName());
        }
    }

    private void checkCategoryExists(Long id) {
        if (!categoryRepo.existsById(id)) {
            throw new CategoryNotFoundException(id);
        }
    }

    @Override
    @Transactional
    public CategoryDto addCategory(CategoryDto dto) {
        final Category category = this.saveWithNameCheck(CategoryMapper.toCategory(dto));
        log.info(CATEGORY_ADDED, category.getId(), category.getName());
        return CategoryMapper.toCategoryDto(category);
    }

    @Override
    @Transactional
    public CategoryDto updateCategory(Long id, CategoryDto dto) {
        Category category = categoryRepo.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));
        category.setName(dto.getName());
        log.info(CATEGORY_UPDATED, category.getId(), category.getName());
        return CategoryMapper.toCategoryDto(category);
    }

    @Override
    @Transactional
    public void deleteCategory(Long id) {
        this.checkCategoryExists(id);
        categoryRepo.deleteById(id);
        log.info(CATEGORY_DELETED, id);
    }

    @Override
    public CategoryDto getCategory(Long id) {
        final Category category = categoryRepo.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));
        log.info(CATEGORY_GET, id);
        return CategoryMapper.toCategoryDto(category);
    }

    @Override
    public List<CategoryDto> detCategoryList(PageRequest page) {
        final List<Category> categories = categoryRepo.findAll(page).toList();
        log.info(CATEGORY_LIST_GET);
        return CategoryMapper.toCategoryDto(categories);
    }
}