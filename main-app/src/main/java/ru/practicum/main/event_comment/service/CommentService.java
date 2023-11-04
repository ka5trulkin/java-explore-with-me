package ru.practicum.main.event_comment.service;

import org.springframework.data.domain.PageRequest;
import ru.practicum.main.event_comment.dto.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto postComment(Long userId, Long eventId, CommentDto dto);

    CommentDto updateComment(Long userId, Long eventId, Long commentId, CommentDto dto);

    void deleteComment(Long userId, Long eventId, Long commentId);

    void deleteComment(Long eventId, Long commentId);

    List<CommentDto> getEventList(Long eventId, PageRequest pageRequest);
}