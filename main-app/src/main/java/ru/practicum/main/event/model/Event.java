package ru.practicum.main.event.model;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import ru.practicum.main.category.mogel.Category;
import ru.practicum.main.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

import static javax.persistence.EnumType.ORDINAL;
import static javax.persistence.GenerationType.IDENTITY;
import static ru.practicum.utils.Patterns.EVENT_WITH_FIELDS;

@NamedEntityGraph(
        name = EVENT_WITH_FIELDS,
        attributeNodes = {
                @NamedAttributeNode("category"),
                @NamedAttributeNode("location"),
                @NamedAttributeNode("initiator")
        })
@Entity
@Table(name = "events")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;
    @Column(name = "annotation", nullable = false, length = 2000)
    private String annotation;
    @Column(name = "title", nullable = false, length = 120)
    private String title;
    @Column(name = "description", nullable = false, length = 7000)
    private String description;
    @Column(name = "eventDate", nullable = false)
    private LocalDateTime eventDate;
    @OneToOne
    @ToString.Exclude
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location;
    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "initiator_id", referencedColumnName = "id")
    private User initiator;
    @Column(name = "createdOn", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdOn;
    @Column(name = "publishedOn")
    private LocalDateTime publishedOn;
    @Column(name = "requestModeration", nullable = false)
    private Boolean requestModeration;
    @Column(name = "confirmedRequests", nullable = false)
    private Integer confirmedRequests;
    @Column(name = "participantLimit", nullable = false)
    private Integer participantLimit;
    @Column(name = "paid", nullable = false)
    private Boolean paid;
    @Enumerated(ORDINAL)
    @Column(name = "state", nullable = false)
    private State state;
    @Column(name = "views", nullable = false)
    private Integer views;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Event event = (Event) o;
        return getId() != null && Objects.equals(getId(), event.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}