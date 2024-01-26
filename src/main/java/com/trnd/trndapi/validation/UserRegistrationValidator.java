package com.trnd.trndapi.validation;

import com.trnd.trndapi.repository.UserRepository;
import com.trnd.trndapi.security.playload.request.SignupRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserRegistrationValidator {
    private final UserRepository userRepository;
    public List<String> validate(SignupRequest signupRequest){
        List<String> errors = new ArrayList<>();
        if (userRepository.existsByMobile(signupRequest.getMobile())) {
            errors.add("Mobile is already exist");
        }

        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            errors.add("Email is already in use!");

        }
        return errors;
    }

}
