package ru.practicum.stats.server.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "stats_box")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatsHit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "app", nullable = false, length = 70)
    private String app;
    @Column(name = "uri", nullable = false, length = 70)
    private String uri;
    @Column(name = "ip", nullable = false, length = 15)
    private String ip;
    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        StatsHit statsHit = (StatsHit) o;
        return getId() != null && Objects.equals(getId(), statsHit.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}