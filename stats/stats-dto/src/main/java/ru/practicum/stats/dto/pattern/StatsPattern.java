package ru.practicum.stats.dto.pattern;

import lombok.experimental.UtilityClass;

@UtilityClass
public class StatsPattern {
    public static final String IP_PATTERN = "((0|1\\d{0,2}|2([0-4][0-9]|5[0-5]))\\.){3}(0|1\\d{0,2}|2([0-4][0-9]|5[0-5]))";
    public static final String DATE_TIME = "yyyy-MM-dd HH:mm:ss";
}