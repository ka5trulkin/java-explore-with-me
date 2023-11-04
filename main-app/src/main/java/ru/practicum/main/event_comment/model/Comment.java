package ru.practicum.main.event_comment.model;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;
import static ru.practicum.utils.Patterns.COMMENT_WITH_FIELDS;

@NamedEntityGraph(
        name = COMMENT_WITH_FIELDS,
        attributeNodes = {
                @NamedAttributeNode("commentator")
        })
@Entity
@Table(name = "comments")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "createdOn", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdOn;
    @Column(name = "text", nullable = false, length = 2000)
    private String text;
    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false, referencedColumnName = "id")
    @ToString.Exclude
    private Event event;
    @ManyToOne
    @JoinColumn(name = "commentator_id", nullable = false, referencedColumnName = "id")
    @ToString.Exclude
    private User commentator;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Comment comment = (Comment) o;
        return getId() != null && Objects.equals(getId(), comment.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}