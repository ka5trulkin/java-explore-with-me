package ru.practicum.main.validation;

import ru.practicum.main.event.dto.LocationDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LocationFieldsValidator implements ConstraintValidator<LocationFields, LocationDto> {
    @Override
    public boolean isValid(LocationDto dto, ConstraintValidatorContext constraintValidatorContext) {
        if (dto != null) {
            return dto.getLon() != null && dto.getLat() != null;
        }
        return true;
    }
}