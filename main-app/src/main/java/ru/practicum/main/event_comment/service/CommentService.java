package ru.practicum.main.event_comment.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.event_comment.dto.CommentDto;

import java.util.List;

public interface CommentService {
    @Transactional
    CommentDto postComment(Long userId, Long eventId, CommentDto dto);

    @Transactional
    CommentDto updateComment(Long userId, Long eventId, Long commentId, CommentDto dto);

    @Transactional
    void deleteComment(Long userId, Long eventId, Long commentId);

    @Transactional
    void deleteComment(Long eventId, Long commentId);

    List<CommentDto> getEventList(Long eventId, PageRequest pageRequest);
}