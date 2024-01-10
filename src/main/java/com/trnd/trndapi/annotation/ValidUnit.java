package com.trnd.trndapi.annotation;

import com.trnd.trndapi.validation.UnitValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UnitValidator.class)
@Target({ElementType.FIELD,ElementType.METHOD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidUnit {
    String message() default "Invalid unit";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
