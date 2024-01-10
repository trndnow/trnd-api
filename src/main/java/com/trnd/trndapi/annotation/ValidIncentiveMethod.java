package com.trnd.trndapi.annotation;

import com.trnd.trndapi.validation.IncentiveMethodValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = IncentiveMethodValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidIncentiveMethod {
    String message() default "Invalid incentive method";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
