package ru.practicum.main.event_comment.mapper;

import com.querydsl.core.Tuple;
import lombok.experimental.UtilityClass;
import ru.practicum.main.event.mapper.EventAbstractMapper;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.event_comment.dto.CommentDto;
import ru.practicum.main.event_comment.model.Comment;
import ru.practicum.main.user.mapper.UserMapper;
import ru.practicum.main.user.model.User;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.main.event.model.QEvent.event;
import static ru.practicum.main.user.model.QUser.user;
import static ru.practicum.utils.message.ExceptionMessage.USER_OR_EVENT_IN_TUPLE_NOT_FOUND;

@UtilityClass
public class CommentMapper extends EventAbstractMapper {
    public static Comment toComment(Tuple tuple, CommentDto dto) {
        checkTupleOrThrow(tuple, USER_OR_EVENT_IN_TUPLE_NOT_FOUND);
        final User commentator = tuple.get(user);
        final Event commentEvent = tuple.get(event);
        return Comment.builder()
                .text(dto.getText())
                .event(commentEvent)
                .commentator(commentator)
                .build();
    }

    public static CommentDto toCommentDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .createdOn(comment.getCreatedOn())
                .text(comment.getText())
                .event(comment.getEvent().getId())
                .commentator(UserMapper.toUserShortDto(comment.getCommentator()))
                .build();
    }

    public static List<CommentDto> toCommentDto(Collection<Comment> comments) {
        return comments.stream()
                .map(CommentMapper::toCommentDto)
                .collect(Collectors.toList());
    }

    public static void updateComment(Comment entity, CommentDto dto) {
        if (!entity.getText().equals(dto.getText())) {
            entity.setText(dto.getText());
        }
    }
}