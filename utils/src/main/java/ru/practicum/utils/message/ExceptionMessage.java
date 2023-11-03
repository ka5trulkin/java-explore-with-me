package ru.practicum.utils.message;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ExceptionMessage {
    public static final String REASON_NOT_FOUND = "Запрашиваемый объект не был найден";
    public static final String REASON_BAD_REQUEST = "Ошибка запроса";
    public static final String REASON_DATA_CONFLICT = "Ошибка в условии для данной операции";
    public static final String REASON_BAD_VALIDATION = "Ошибка валидации";
    public static final String REASON_INTERNAL_SERVER_ERROR = "Внутренняя ошибка сервера";
    public static final String REASON_MISSING_REQUEST_PARAMETER = "Внутренняя ошибка сервера";
    public static final String USER_NOT_FOUND = "Пользователь с ID:'%s' не найден";
    public static final String USER_OR_CATEGORY_IN_TUPLE_NOT_FOUND = "Пользователь или категория не найдены";
    public static final String USER_OR_EVENT_IN_TUPLE_NOT_FOUND = "Пользователь или событие не найдены";
    public static final String USER_OR_EVENT_NOT_FOUND = "Пользователь ID:'%s' или событие ID:'%s' не найдены";
    public static final String CATEGORY_NOT_FOUND = "Категория ID:'%s' не найдена";
    public static final String USER_EMAIL_ALREADY_EXIST = "Пользователь email:'%s' уже существует";
    public static final String EVENT_REQUEST_ALREADY_EXIST = "Запрос на событие ID:'%s' уже существует";
    public static final String EVENT_REQUEST_REQUESTER_IS_INITIATOR = "Пользователь является владельцем события ID:'%s'";
    public static final String CATEGORY_NAME_ALREADY_EXIST = "Категория Name:'%s' уже существует";
    public static final String EVENT_NOT_PUBLISHED_EXIST = "Событие ID:'%s' не опубликовано";
    public static final String EVENT_UPDATE_STATUS_REQUEST = "Ошибка обновления статуса запроса события ID:'%s'";
    public static final String EVENT_PARTICIPANT_LIMIT = "Достигнут лимит запросов на участие в событии ID:'%s'";
    public static final String EVENT_PUBLISHING = "Ошибка публикации события ID:'%s'. " +
            "Событие можно изменить только в состоянии ожидания публикации";
    public static final String EVENT_CANCELING = "Ошибка отклонения события ID:'%s'. " +
            "Событие нельзя отклонить в состоянии ожидания публикации";
    public static final String EVENT_REQUEST_NOT_FOUND = "Событие ID:'%s' не найдено";
    public static final String EVENT_NOT_FOUND = "Событие ID:'%s' не найдено";
    public static final String COMPILATION_NOT_FOUND = "Подборка событий ID:'%s' не найдена";
    public static final String COMMENT_NOT_FOUND = "Комментарий ID:'%s' не найден";
    public static final String EVENT_START_END_BAD = "Конец события должен быть до его начала, дурень!";
}