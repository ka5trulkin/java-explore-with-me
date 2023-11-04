package ru.practicum.main.event_comment.service;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.event.model.State;
import ru.practicum.main.event.storage.EventRepository;
import ru.practicum.main.event_comment.dto.CommentDto;
import ru.practicum.main.event_comment.mapper.CommentMapper;
import ru.practicum.main.event_comment.model.Comment;
import ru.practicum.main.event_comment.storage.CommentRepository;
import ru.practicum.main.exception.child.CommentNotFoundException;
import ru.practicum.utils.QPredicate;

import javax.persistence.EntityManager;
import java.util.List;

import static ru.practicum.main.event.model.QEvent.event;
import static ru.practicum.main.event_comment.model.QComment.comment;
import static ru.practicum.main.user.model.QUser.user;
import static ru.practicum.utils.message.LogMessage.*;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepo;
    private final EventRepository eventRepo;
    private final EntityManager manager;

    private void checkCommentExists(Predicate predicate, Long commentId) {
        if (!commentRepo.exists(predicate)) {
            throw new CommentNotFoundException(commentId);
        }
    }

    private Predicate getCommentPredicate(Long eventId, Long commentId) {
        return QPredicate.builder()
                .add(comment.id.eq(commentId))
                .add(comment.event.id.eq(eventId))
                .buildAnd();
    }

    private Predicate getCommentPredicate(Long userId, Long eventId, Long commentId) {
        return QPredicate.builder()
                .add(this.getCommentPredicate(eventId, commentId))
                .add(comment.commentator.id.eq(userId))
                .buildAnd();
    }
//
    @Override
    @Transactional
    public CommentDto postComment(Long userId, Long eventId, CommentDto dto) {
        final Predicate predicate = QPredicate.builder()
                .add(user.id.eq(userId))
                .add(event.id.eq(eventId))
                .add(event.state.ne(State.PENDING))
                .buildAnd();
        final Tuple userAndEvent = eventRepo.getUserAndEvent(predicate, manager);
        final Comment comment = commentRepo.save(CommentMapper.toComment(userAndEvent, dto));
        log.info(COMMENT_ADDED, userId, eventId);
        return CommentMapper.toCommentDto(comment);
    }

    @Override
    @Transactional
    public CommentDto updateComment(Long userId, Long eventId, Long commentId, CommentDto dto) {
        final Predicate predicate = this.getCommentPredicate(userId, eventId, commentId);
        final Comment entity = commentRepo.findOne(predicate)
                .orElseThrow(() -> new CommentNotFoundException(commentId));
        CommentMapper.updateComment(entity, dto);
        log.info(COMMENT_UPDATED, commentId, userId, eventId);
        return CommentMapper.toCommentDto(entity);
    }

    @Override
    @Transactional
    public void deleteComment(Long userId, Long eventId, Long commentId) {
        final Predicate predicate = this.getCommentPredicate(userId, eventId, commentId);
        this.checkCommentExists(predicate, commentId);
        commentRepo.deleteById(commentId);
        log.info(COMMENT_DELETED, commentId, userId, eventId);
    }

    @Override
    @Transactional
    public void deleteComment(Long eventId, Long commentId) {
        final Predicate predicate = this.getCommentPredicate(eventId, commentId);
        this.checkCommentExists(predicate, commentId);
        commentRepo.deleteById(commentId);
        log.info(COMMENT_DELETED_ADMIN, commentId, eventId);
    }

    @Override
    public List<CommentDto> getEventList(Long eventId, PageRequest page) {
        final Predicate predicate = comment.event.id.eq(eventId);
        final List<Comment> comments = commentRepo.findAll(predicate, page).toList();
        log.info(GET_COMMENT_LIST, eventId);
        return CommentMapper.toCommentDto(comments);
    }
}