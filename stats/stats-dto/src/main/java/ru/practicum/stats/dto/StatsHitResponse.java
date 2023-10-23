package ru.practicum.stats.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@Getter
public class StatsHitResponse extends StatsHitDto {
    private long hits;
}