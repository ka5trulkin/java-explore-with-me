package ru.practicum.main.event.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@Getter
public class LocationDto {
    private Double lat;
    private Double lon;
}