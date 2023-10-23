package ru.practicum.utils.message;

import lombok.experimental.UtilityClass;

@UtilityClass
public class LogMessage {
    //user
    public static final String REQUEST_ADD_USER = "Запрос на добавление пользователя Email:'{}'";
    public static final String REQUEST_DELETE_USER = "Запрос на удаление пользователя ID:'{}'";
    public static final String REQUEST_GET_USER_LIST = "Запрос на получение списка пользователей";
    public static final String USER_ADDED = "Пользователь ID:'{}'; Email:'{}' добавлен";
    public static final String USER_DELETED = "Пользователь ID:'{}' удален";
    public static final String USER_LIST_GET = "Получение списка пользователей";
    //category
    public static final String REQUEST_ADD_CATEGORY = "Запрос на добавление категории Name:'{}'";
    public static final String REQUEST_DELETE_CATEGORY = "Запрос на удаление категории ID:'{}'";
    public static final String REQUEST_UPDATE_CATEGORY = "Запрос на обновление категории ID:'{}'; Name:'{}'";
    public static final String REQUEST_CATEGORY_GET = "Запрос на получение категории ID:'{}'";
    public static final String REQUEST_CATEGORY_LIST_GET = "Запрос на получение списка категорий";
    public static final String CATEGORY_ADDED = "Категория ID:'{}'; Name:'{}' добавлена";
    public static final String CATEGORY_UPDATED = "Категория ID:'{}'; Name:'{}' обновлена";
    public static final String CATEGORY_DELETED = "Категория ID:'{}' удалена";
    public static final String CATEGORY_GET = "Получение категории ID:'{}'";
    public static final String CATEGORY_LIST_GET = "Получение списка категорий";
    //event
    public static final String REQUEST_ADD_EVENT = "Запрос на добавление события Title:'{}'";
    public static final String REQUEST_GET_EVENT = "Запрос на получение события ID:'{}'";
    public static final String REQUEST_GET_EVENT_LIST_BY_INITIATOR = "Запрос на получение списка событий пользователя ID:'{}'";
    public static final String REQUEST_GET_EVENT_LIST = "Запрос на получение списка событий";
    public static final String REQUEST_UPDATE_EVENT = "Запрос на обновление события ID:'{}'";
    public static final String EVENT_ADDED = "Событие ID:'{}'; Title:'{}' добавлено";
    public static final String GET_EVENT = "Получение события ID:'{}'";
    public static final String GET_EVENT_LIST_BY_INITIATOR = "Получение списка событий пользователя ID:'{}'";
    public static final String GET_EVENT_LIST = "Получение списка событий";
    public static final String EVENT_UPDATED = "Событие ID:'{}' обновлено";
    //eventRequest
    public static final String REQUEST_ADD_EVENT_REQUEST = "Запрос на добавление в событие ID:'{}'";
    public static final String REQUEST_CANCEL_EVENT_REQUEST = "Запрос на отмену участия в события ID:'{}'";
    public static final String REQUEST_GET_EVENT_REQUEST_LIST = "Запрос на получение списка заявок в событиях пользователя ID:'{}'";
    public static final String REQUEST_UPDATE_EVENT_REQUEST_STATUS = "Запрос на обновление статусов списка заявок события ID:'{}'";
    public static final String EVENT_REQUEST_ADDED = "Запрос на событие ID:'{}'добавлен";
    public static final String EVENT_REQUEST_CANCELED = "Запрос на участие в событии ID:'{}' отменен";
    public static final String GET_EVENT_REQUEST_LIST = "Получение списка заявок в событиях пользователя ID:'{}'";
    public static final String EVENT_REQUEST_STATUS_UPDATED = "Статусы списка заявок события ID:'{}' обновлены";
    //compilation
    public static final String REQUEST_ADD_COMPILATION = "Запрос на добавление подборки событий Title:'{}'";
    public static final String REQUEST_UPDATE_COMPILATION = "Запрос на обновление подборки событий ID:'{}'";
    public static final String REQUEST_DELETE_COMPILATION = "Запрос на удаление подборки событий ID:'{}'";
    public static final String REQUEST_GET_COMPILATION = "Запрос на получение подборки событий ID:'{}'";
    public static final String REQUEST_GET_COMPILATION_LIST = "Запрос на получение списка подборок событий";

    public static final String COMPILATION_ADDED = "Подборка событий ID:'{}'; Title:'{}' добавлена";
    public static final String COMPILATION_UPDATED = "Подборка событий ID:'{}' обновлена";
    public static final String COMPILATION_DELETED = "Подборка событий ID:'{}' удалена";
    public static final String GET_COMPILATION = "Получение подборки событий ID:'{}'";
    public static final String GET_COMPILATION_LIST = "Получение списка подборок событий";

    //stats
    public static final String REQUEST_POST_HIT = "Запрос на сохранение информации обращения к URI:'{}'";
    public static final String POST_HIT = "Информация обращения к URI:'{}' сохранена";
    public static final String REQUEST_GET_STATS = "Запрос на получение статистики в период с '{}' по '{}'";
    public static final String STATS_GET = "Получение статистики в период с '{}' по '{}'";
}