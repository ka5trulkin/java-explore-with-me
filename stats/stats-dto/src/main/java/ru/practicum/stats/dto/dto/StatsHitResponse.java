package ru.practicum.stats.dto.dto;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class StatsHitResponse extends StatsHitDto {
    private long hits;
}