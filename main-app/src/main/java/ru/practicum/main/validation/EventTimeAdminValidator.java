package ru.practicum.main.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

public class EventTimeAdminValidator implements ConstraintValidator<EventTimeAdmin, LocalDateTime> {
    @Override
    public boolean isValid(LocalDateTime start, ConstraintValidatorContext constraintValidatorContext) {
        final LocalDateTime requiredTime = LocalDateTime.now().plusHours(1);
            return start == null || start.isAfter(requiredTime);
    }
}