package ru.practicum.utils;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

import static ru.practicum.utils.Patterns.DATE_TIME;

@Getter
@RequiredArgsConstructor
public class ApiError {
    private final HttpStatus status;
    private final String reason;
    private final String message;
    @JsonFormat(pattern = DATE_TIME)
    private final LocalDateTime timestamp = LocalDateTime.now();

    public ApiError(String message, String reason, HttpStatus status) {
        this.message = message;
        this.reason = reason;
        this.status = status;
    }
}