package com.library.api.helpers.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = DateValidator.class)
@Target({ FIELD })
@Retention(RUNTIME)
public @interface ValidDate {
    String message() default "A data deve ser informada no formato 'YYYY-MM-DD'";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
