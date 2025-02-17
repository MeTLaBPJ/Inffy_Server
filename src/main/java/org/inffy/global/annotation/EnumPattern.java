package org.inffy.global.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumPatternValidator.class)
public @interface EnumPattern {

    // 오류 발생 시 메세지
    String message() default "";

    // 상황 별 validation 제어
    Class<?>[] groups() default {};

    // 심각도
    Class<? extends Payload>[] payload() default {};

    // 제약할 클래스 지정
    Class<? extends Enum<?>> enumClass();

    // 대소문자 구별 여부
    boolean ignoreCase() default false;
}
