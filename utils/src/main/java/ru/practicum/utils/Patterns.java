package ru.practicum.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Patterns {
    public static final String DATE_TIME = "yyyy-MM-dd HH:mm:ss";
    public static final String MAIN_APP = "Main App";
    public static final String HIT_PREFIX = "/hit";
    public static final String STATS_PREFIX = "/stats";
    public static final String USER_ADMIN_PREFIX = "/admin/users";
    public static final String CATEGORY_ADMIN_PREFIX = "/admin/categories";
    public static final String CATEGORY_PUBLIC_PREFIX = "/categories";
    public static final String EVENT_PRIVATE_PREFIX = "/users/{userId}/events";
    public static final String EVENT_ADMIN_PREFIX = "/admin/events";
    public static final String EVENT_PUBLIC_PREFIX = "/events";
    public static final String EVENT_REQUEST_PREFIX = "/users/{userId}/requests";
    public static final String EVENT_WITH_FIELDS = "EventWithFields";
    public static final String COMMENT_WITH_FIELDS = "CommentWithFields";
    public static final String EVENT_REQUEST_WITH_FIELDS = "EventRequestWithFields";
    public static final String EVENT_PATH = String.join("", EVENT_PUBLIC_PREFIX, "/%d");
    public static final String COMPILATION_ADMIN_PREFIX = "/admin/compilations";
    public static final String COMPILATION_PUBLIC_PREFIX = "/compilations";
    public static final String COMPILATION_WITH_FIELDS = "CompilationWithFields";
}