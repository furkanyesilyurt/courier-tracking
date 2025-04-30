package com.furkanyesilyurt.couriertracking.common.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NumericValidator implements ConstraintValidator<Numeric, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value.isBlank()) {
            return false;
        }

        return value.matches("\\d{11}");
    }
}
