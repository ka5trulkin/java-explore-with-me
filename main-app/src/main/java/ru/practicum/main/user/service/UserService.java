package ru.practicum.main.user.service;

import org.springframework.data.domain.PageRequest;
import ru.practicum.main.user.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto postUser(UserDto dto);

    List<UserDto> getUserList(List<Long> ids, PageRequest page);

     void deleteUser(Long id);
}