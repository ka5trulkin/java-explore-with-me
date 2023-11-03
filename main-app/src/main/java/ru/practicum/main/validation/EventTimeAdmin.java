package ru.practicum.main.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static ru.practicum.utils.message.ValidationMessage.EVENT_DATE_ONE_HOUR_LATER;

@Target(FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = EventTimeAdminValidator.class)
@Documented
public @interface EventTimeAdmin {
    String message() default EVENT_DATE_ONE_HOUR_LATER;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}