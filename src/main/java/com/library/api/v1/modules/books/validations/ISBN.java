package com.library.api.v1.modules.books.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Documented
@Constraint(validatedBy = ISBNValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ISBN {
    String message() default "ISBN invalido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
