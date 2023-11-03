package ru.practicum.main.compilation.model;

import lombok.*;
import org.hibernate.Hibernate;
import ru.practicum.main.event.model.Event;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;
import static ru.practicum.utils.Patterns.COMPILATION_WITH_FIELDS;

@NamedEntityGraph(
        name = COMPILATION_WITH_FIELDS,
        attributeNodes = @NamedAttributeNode(value = "events", subgraph = "event"),
        subgraphs = {
                @NamedSubgraph(name = "event", attributeNodes = @NamedAttributeNode("category")),
                @NamedSubgraph(name = "event", attributeNodes = @NamedAttributeNode("location")),
                @NamedSubgraph(name = "event", attributeNodes = @NamedAttributeNode("initiator"))
        })
@Entity
@Table(name = "compilations")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Compilation {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "pinned", nullable = false)
    private Boolean pinned;
    @Column(name = "title", nullable = false, length = 50)
    private String title;
    @ManyToMany
    @JoinTable(name = "compilations_events",
            joinColumns = @JoinColumn(name = "compilation_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "event_id", referencedColumnName = "id"))
    @ToString.Exclude
    private Set<Event> events;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Compilation that = (Compilation) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}