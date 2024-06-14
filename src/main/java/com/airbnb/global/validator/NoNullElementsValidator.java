package com.airbnb.global.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Collection;

public class NoNullElementsValidator implements ConstraintValidator<NoNullElements, Collection<?>> {

    @Override
    public boolean isValid(Collection<?> collection, ConstraintValidatorContext context) {
        if (collection == null) {
            return true;  // @NotNull이 이미 적용되어 있으므로 여기서 처리하지 않음
        }

        for (Object element : collection) {
            if (element == null) {
                return false;
            }
        }

        return true;
    }
}
