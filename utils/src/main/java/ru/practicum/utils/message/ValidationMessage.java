package ru.practicum.utils.message;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ValidationMessage {
    //annotation
    public static final String ANNOTATION_SHOULD_NOT_BE_BLANK = "Поле Annotation не должно быть пустым";
    public static final String ANNOTATION_BAD_SIZE_MAX_50 = "Количество символов в поле Annotation должно быть от 20 до 2000";
    //description
    public static final String DESCRIPTION_SHOULD_NOT_BE_BLANK = "Поле Description не должно быть пустым";
    public static final String DESCRIPTION_BAD_SIZE_MAX_50 = "Количество символов в поле Description должно быть от 20 до 7000";
    //email
    public static final String EMAIL_SHOULD_NOT_BE_BLANK = "Поле Email не должно быть пустым";
    public static final String EMAIL_BAD = "Поле Email не соответствует формату электронной почты";
    public static final String EMAIL_BAD_SIZE_MAX_250 = "Количество символов в поле Email должно быть от 6 до 254";
    //name
    public static final String NAME_SHOULD_NOT_BE_BLANK = "Поле Name не должно быть пустым";
    public static final String NAME_BAD_SIZE_MAX_50 = "Количество символов в поле Name должно быть от 1 до 50";
    public static final String NAME_BAD_SIZE_MAX_250 = "Количество символов в поле Name должно быть от 2 до 250";
    //time
    public static final String TIMESTAMP_SHOULD_NOT_BE_NULL = "Поле timestamp не должно быть пустым";
    public static final String TIME_SHOULD_NOT_BE_FUTURE = "Указанное время не должно быть в будущем";
    //category
    public static final String CATEGORY_SHOULD_NOT_BE_BLANK = "Поле Category не должно быть пустым";
    //event
    public static final String EVENT_DATE_TWO_HOURS_LATER = "Событие не может быть раньше, чем за два часа до текущего момента";
    public static final String EVENT_DATE_ONE_HOUR_LATER = "Событие не может быть раньше, чем за один час до текущего момента";
    public static final String EVENT_DATE_SHOULD_NOT_BE_NULL = "Поле Event Date не должно быть пустым";
    //location
    public static final String LOCATION_SHOULD_NOT_BE_BLANK = "Поле Location не должно быть пустым";
    public static final String LOCATION_FIELDS_NOT_BE_NULL = "Поля Location не должны быть пустыми";
    //title
    public static final String TITLE_BAD_SIZE_MAX_50 = "Количество символов в поле Title должно быть от 1 до 50";
    public static final String TITLE_SHOULD_NOT_BE_BLANK = "Поле Title не должно быть пустым";
    public static final String TITLE_BAD_SIZE_MAX_120 = "Количество символов в поле Title должно быть от 3 до 120";
    //other
    public static final String APP_SHOULD_NOT_BE_BLANK = "Поле App не должно быть пустым";
    public static final String IP_SHOULD_NOT_BE_BLANK = "Поле Ip не должно быть пустым";
    public static final String URI_SHOULD_NOT_BE_BLANK = "Поле Uri не должно быть пустым";
}