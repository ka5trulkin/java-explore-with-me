package ru.practicum.main.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.practicum.main.exception.base.DataConflictException;
import ru.practicum.main.exception.base.NotFoundException;
import ru.practicum.main.exception.base.RequestException;
import ru.practicum.utils.ApiError;

import java.util.stream.Collectors;

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
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError badRequest(RequestException exception) {
        log.error(exception.getMessage(), exception);
        return new ApiError(exception.getMessage(), REASON_BAD_REQUEST, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError badRequest(MethodArgumentTypeMismatchException exception) {
        log.error(exception.getMessage(), exception);
        return new ApiError(exception.getMessage(), REASON_BAD_REQUEST, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError badRequest(MissingServletRequestParameterException exception) {
        log.error(exception.getMessage(), exception);
        return new ApiError(exception.getMessage(), REASON_BAD_REQUEST, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError notFound(NotFoundException exception) {
        log.error(exception.getMessage(), exception);
        return new ApiError(exception.getMessage(), REASON_NOT_FOUND, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError dataConflict(DataIntegrityViolationException exception) {
        log.error(exception.getMessage(), exception);
        return new ApiError(exception.getMessage(), REASON_BAD_REQUEST, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError dataConflict(DataConflictException exception) {
        log.error(exception.getMessage(), exception);
        return new ApiError(exception.getMessage(),REASON_BAD_REQUEST, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError badValidation(MethodArgumentNotValidException exception) {
        String exceptionMessage = getValidationExceptionMessage(exception);
        log.error(exceptionMessage, exception);
        return new ApiError(exceptionMessage, REASON_BAD_VALIDATION, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError badValidation(MissingRequestHeaderException exception) {
        log.error(exception.getMessage(), exception);
        return new ApiError(exception.getMessage(), REASON_BAD_VALIDATION, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError internalServerError(Throwable exception) {
        log.error(exception.getMessage(), exception);
        return new ApiError(exception.getMessage(), REASON_INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}