package ru.practicum.main.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.user.dto.UserDto;
import ru.practicum.main.user.service.UserService;
import ru.practicum.utils.PageApp;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static ru.practicum.utils.Patterns.USER_ADMIN_PREFIX;
import static ru.practicum.utils.message.LogMessage.*;

@RestController
@Slf4j
@RequestMapping(path = USER_ADMIN_PREFIX)
@RequiredArgsConstructor
@Validated
public class UserController {
    private final UserService userService;

    @PostMapping
    @ResponseStatus(CREATED)
    public UserDto postUser(@Valid @RequestBody UserDto dto) {
        log.info(REQUEST_ADD_USER, dto.getEmail());
        return userService.postUser(dto);
    }

    @GetMapping
    public List<UserDto> getUsers(
            @RequestParam(required = false) List<Long> ids,
            @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
            @RequestParam(defaultValue = "10") @Positive Integer size
    ) {
        log.info(REQUEST_GET_USER_LIST);
        return userService.getUserList(ids, PageApp.ofStartingIndex(from, size));
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(NO_CONTENT)
    public void deleteUser(@PathVariable @Positive Long userId) {
        log.info(REQUEST_DELETE_USER, userId);
        userService.deleteUser(userId);
    }
}