package ru.practicum.stats.dto.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ValidationMessage {
    public static final String APP_SHOULD_NOT_BE_BLANK = "Поле App не должно быть пустым";
    public static final String URI_SHOULD_NOT_BE_BLANK = "Поле Uri не должно быть пустым";
    public static final String IP_SHOULD_NOT_BE_BLANK = "Поле Ip не должно быть пустым";
    public static final String IP_FORMAT_ERROR = "Неверный формат поля Ip";
    public static final String TIMESTAMP_SHOULD_NOT_BE_NULL = "Поле timestamp не должно быть пустым";
    public static final String TIME_SHOULD_NOT_BE_FUTURE = "Указанное время не должно быть в будущем";
}