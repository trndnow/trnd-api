package com.trnd.trndapi.validation;

import com.trnd.trndapi.annotation.ValidSignupRequest;
import com.trnd.trndapi.enums.ERole;
import com.trnd.trndapi.repository.UserRepository;
import com.trnd.trndapi.security.playload.request.SignupRequest;
import com.trnd.trndapi.service.MerchantService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class SignupRequestValidator implements ConstraintValidator<ValidSignupRequest, SignupRequest> {


    private final MerchantService merchantService;
    private final UserRepository userRepository;
    public SignupRequestValidator(MerchantService merchantService, UserRepository userRepository) {
        this.merchantService = merchantService;
        this.userRepository = userRepository;
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
    public boolean isValid(SignupRequest signupRequest, ConstraintValidatorContext context) {

        /** Check if Admin already exist dont create the account. */
        if(signupRequest.getRole().equals(ERole.ROLE_ADMIN)){
            Boolean userByRoleName = userRepository.existsUserByRoleName(signupRequest.getRole());
            if(userByRoleName){
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("Cannot register as ADMIN")
                        .addPropertyNode("role")
                        .addConstraintViolation();
                return false;
            }

        }

        if (signupRequest.getRole() == ERole.ROLE_MERCHANT) {
            if (signupRequest.getMerchantName() == null || signupRequest.getMerchantName().isEmpty()) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("Merchant name is mandatory for MERCHANT")
                        .addPropertyNode("merchantName")
                        .addConstraintViolation();
                return false;
            }

            // Additional validations for ROLE_MERCHANT if needed
        }

        if (signupRequest.getRole() == ERole.ROLE_AFFILIATE) {
            if (signupRequest.getMerchantCode() == null || signupRequest.getMerchantCode().isEmpty()) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("Merchant code is mandatory for AFFILIATE")
                        .addPropertyNode("merchantCode")
                        .addConstraintViolation();
                return false;
            }
            if (!merchantService.isValidMerchantCode(signupRequest.getMerchantCode())) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("Invalid merchant code for ROLE_AFFILIATE")
                        .addPropertyNode("merchantCode")
                        .addConstraintViolation();
                return false;
            }

            // Additional validations for ROLE_AFFILIATE if needed, including checking if it's a valid merchant code
        }

        return true;
    }
}
