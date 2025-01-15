package org.inffy.global.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EnumPatternValidator implements ConstraintValidator<EnumPattern, String> {

    private EnumPattern enumPattern;

    @Override
    public void initialize(EnumPattern constraintAnnotation) {
        this.enumPattern = constraintAnnotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            return true; // null은 @NotNull에서 처리
        }

        Object[] enumValues = this.enumPattern.enumClass().getEnumConstants();
        if (enumValues != null){
            for (Object enumValue : enumValues) {
                if (enumValue.toString().equals(value)){
                    return true;
                }
            }
        }
        return false;
    }
}
