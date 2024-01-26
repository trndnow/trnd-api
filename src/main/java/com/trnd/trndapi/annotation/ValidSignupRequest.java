package com.trnd.trndapi.annotation;

import com.trnd.trndapi.validation.SignupRequestValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = SignupRequestValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidSignupRequest {
    String message() default "Invalid signup request";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
