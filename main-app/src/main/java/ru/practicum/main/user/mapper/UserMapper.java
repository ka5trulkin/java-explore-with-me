package ru.practicum.main.user.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.main.user.dto.UserDto;
import ru.practicum.main.user.dto.UserShortDto;
import ru.practicum.main.user.model.User;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class UserMapper {
    public User toUser(UserDto dto) {
        return User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .build();
    }

    public UserDto toUserResponse(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    public List<UserDto> toUserResponse(Collection<User> users) {
        return users.stream()
                .map(UserMapper::toUserResponse)
                .collect(Collectors.toList());
    }

    public UserShortDto toUserShortDto(User user) {
        return UserShortDto.builder()
                .id(user.getId())
                .name(user.getName())
                .build();
    }
}