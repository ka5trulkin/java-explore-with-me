package ru.practicum.stats.server.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import ru.practicum.stats.dto.pattern.StatsPattern;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class ResponseError {
    private final HttpStatus status;
    private final String error;
    @JsonFormat(pattern = StatsPattern.DATE_TIME)
    private final LocalDateTime time = LocalDateTime.now();

    public ResponseError(String error, HttpStatus status) {
        this.error = error;
        this.status = status;
    }
}