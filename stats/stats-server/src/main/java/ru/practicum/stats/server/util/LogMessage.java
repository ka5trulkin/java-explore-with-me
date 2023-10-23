package ru.practicum.stats.server.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class LogMessage {
    public static final String REQUEST_POST_HIT = "Запрос на сохранение информации обращения к URI:'{}'";
    public static final String POST_HIT = "Информация обращения к URI:'{}' сохранена";
    public static final String REQUEST_GET_STATS = "Запрос на получение статистики в период с '{}' по '{}'";
    public static final String GET_STATS = "Получение статистики в период с '{}' по '{}'";
}