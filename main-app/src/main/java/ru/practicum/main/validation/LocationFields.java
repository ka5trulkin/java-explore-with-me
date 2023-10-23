package ru.practicum.main.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static ru.practicum.utils.message.ValidationMessage.LOCATION_FIELDS_NOT_BE_NULL;

@Target({FIELD, TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = LocationFieldsValidator.class)
@Documented
public @interface LocationFields {
    String message() default LOCATION_FIELDS_NOT_BE_NULL;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}