package ru.practicum.main.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.category.dto.CategoryDto;
import ru.practicum.main.category.service.CategoryServiceImpl;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static ru.practicum.utils.Patterns.CATEGORY_ADMIN_PREFIX;
import static ru.practicum.utils.message.LogMessage.*;

@RestController
@Slf4j
@RequestMapping(path = CATEGORY_ADMIN_PREFIX)
@RequiredArgsConstructor
@Validated
public class CategoryAdminController {
    private final CategoryServiceImpl categoryService;

    @PostMapping
    @ResponseStatus(CREATED)
    public CategoryDto postCategory(@Valid @RequestBody CategoryDto dto) {
        log.info(REQUEST_ADD_CATEGORY, dto.getName());
        return categoryService.addCategory(dto);
    }

    @PatchMapping("/{catId}")
    public CategoryDto updateCategory(@PathVariable @Positive Long catId, @Valid @RequestBody CategoryDto dto) {
        log.info(REQUEST_UPDATE_CATEGORY, catId, dto.getName());
        return categoryService.updateCategory(catId, dto);
    }

    @DeleteMapping("/{catId}")
    @ResponseStatus(NO_CONTENT)
    public void deleteCategory(@PathVariable @Positive Long catId) {
        log.info(REQUEST_DELETE_CATEGORY, catId);
        categoryService.deleteCategory(catId);
    }
}