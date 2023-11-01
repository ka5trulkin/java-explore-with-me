package ru.practicum.main.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.category.dto.CategoryDto;
import ru.practicum.main.category.service.CategoryServiceImpl;
import ru.practicum.utils.PageApp;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

import static ru.practicum.utils.Patterns.CATEGORY_PUBLIC_PREFIX;
import static ru.practicum.utils.message.LogMessage.REQUEST_CATEGORY_GET;
import static ru.practicum.utils.message.LogMessage.REQUEST_CATEGORY_LIST_GET;

@RestController
@Slf4j
@RequestMapping(path = CATEGORY_PUBLIC_PREFIX)
@RequiredArgsConstructor
@Validated
public class CategoryPublicController {
    private final CategoryServiceImpl categoryService;

    @GetMapping("/{catId}")
    public CategoryDto getCategory(@PathVariable @Positive Long catId) {
        log.info(REQUEST_CATEGORY_GET, catId);
        return categoryService.getCategory(catId);
    }

    @GetMapping
    public List<CategoryDto> getCategoryList(
            @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
            @RequestParam(defaultValue = "10") @Positive Integer size
    ) {
        log.info(REQUEST_CATEGORY_LIST_GET);
        return categoryService.detCategoryList(PageApp.ofStartingIndex(from, size));
    }
}