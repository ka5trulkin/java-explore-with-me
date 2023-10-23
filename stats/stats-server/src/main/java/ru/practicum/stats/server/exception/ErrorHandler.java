package ru.practicum.stats.server.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.utils.ApiError;

import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static ru.practicum.utils.message.ExceptionMessage.*;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {
    private String getValidationExceptionMessage(MethodArgumentNotValidException exception) {
        return exception.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList())
                .toString();
    }

    @ExceptionHandler
    @ResponseStatus(BAD_REQUEST)
    public ApiError badValidation(MethodArgumentNotValidException exception) {
        String exceptionMessage = getValidationExceptionMessage(exception);
        log.error(exceptionMessage, exception);
        return new ApiError(exceptionMessage, REASON_BAD_VALIDATION, BAD_REQUEST);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError missingRequestParameter(MissingServletRequestParameterException exception) {
        log.error(exception.getMessage(), exception);
        return new ApiError(exception.getMessage(), REASON_MISSING_REQUEST_PARAMETER, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ApiError internalServerError(Throwable exception) {
        log.error(exception.getMessage(), exception);
        return new ApiError(exception.getMessage(), REASON_INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR);
    }
}