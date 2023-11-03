package ru.practicum.main.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.exception.child.UserEmailAlreadyExistException;
import ru.practicum.main.exception.child.UserNotFoundException;
import ru.practicum.main.user.dto.UserDto;
import ru.practicum.main.user.mapper.UserMapper;
import ru.practicum.main.user.model.User;
import ru.practicum.main.user.storage.UserRepository;

import java.util.List;

import static ru.practicum.main.user.model.QUser.user;
import static ru.practicum.utils.message.LogMessage.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepo;

    private User saveWithEmailCheck(User user) {
        try {
            return userRepo.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new UserEmailAlreadyExistException(user.getEmail());
        }
    }

    private void checkUserExists(Long id) {
        if (!userRepo.existsById(id)) {
            throw new UserNotFoundException(id);
        }
    }

    @Override
    @Transactional
    public UserDto postUser(UserDto dto) {
        final User user = this.saveWithEmailCheck(UserMapper.toUser(dto));
        log.info(USER_ADDED, user.getId(), user.getEmail());
        return UserMapper.toUserResponse(user);
    }

    @Override
    public List<UserDto> getUserList(List<Long> ids, PageRequest page) {
        final List<User> users = ids == null
                ? userRepo.findAll(page).toList()
                : userRepo.findAll(user.id.in(ids), page).toList();
        log.info(USER_LIST_GET);
        return UserMapper.toUserResponse(users);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        this.checkUserExists(id);
        userRepo.deleteById(id);
        log.info(USER_DELETED, id);
    }
}