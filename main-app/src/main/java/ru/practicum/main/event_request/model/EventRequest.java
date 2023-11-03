package ru.practicum.main.event_request.model;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

import static javax.persistence.EnumType.ORDINAL;
import static javax.persistence.GenerationType.IDENTITY;
import static ru.practicum.utils.Patterns.EVENT_REQUEST_WITH_FIELDS;

@NamedEntityGraph(
        name = EVENT_REQUEST_WITH_FIELDS,
        attributeNodes = {
                @NamedAttributeNode("requester"),
                @NamedAttributeNode("event")
        })
@Entity
@Table(name = "event_requests")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventRequest {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "created", nullable = false)
    @CreationTimestamp
    private LocalDateTime created;
    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "event_id", referencedColumnName = "id")
    private Event event;
    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "requester_id", referencedColumnName = "id")
    private User requester;
    @Enumerated(ORDINAL)
    @Column(name = "status")
    private Status status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        EventRequest that = (EventRequest) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}