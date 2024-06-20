package com.airbnb.global.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Collection;

public class NoBlankStringsValidator implements ConstraintValidator<NoBlankStrings, Collection<CharSequence>> {

    @Override
    public boolean isValid(Collection<CharSequence> collection, ConstraintValidatorContext context) {
        if (collection == null) {
            return true;  // 아예 빈 값은 허용함. 아니라면 @NotNull 설정
        }

        for (CharSequence cs : collection) {
            if(cs.toString().trim().isEmpty()) {
                return false;
            }
        }

        return true;
    }
}