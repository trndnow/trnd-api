package com.trnd.trndapi.validation;

import com.trnd.trndapi.annotation.ValidIncentiveMethod;
import com.trnd.trndapi.enums.IncentiveMethod;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class IncentiveMethodValidator implements ConstraintValidator<ValidIncentiveMethod, IncentiveMethod>{
    /**
     * Initializes the validator in preparation for
     * {@link #isValid(Object, ConstraintValidatorContext)} calls.
     * The constraint annotation for a given constraint declaration
     * is passed.
     * <p>
     * This method is guaranteed to be called before any use of this instance for
     * validation.
     * <p>
     * The default implementation is a no-op.
     *
     * @param constraintAnnotation annotation instance for a given constraint declaration
     */
    @Override
    public void initialize(ValidIncentiveMethod constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    /**
     * Implements the validation logic.
     * The state of {@code value} must not be altered.
     * <p>
     * This method can be accessed concurrently, thread-safety must be ensured
     * by the implementation.
     *
     * @param value   object to validate
     * @param context context in which the constraint is evaluated
     * @return {@code false} if {@code value} does not pass the constraint
     */
    @Override
    public boolean isValid(IncentiveMethod value, ConstraintValidatorContext context) {
        if(value == null){
            return false;
        }
        return Arrays.asList(IncentiveMethod.values()).contains(value);
    }
}
